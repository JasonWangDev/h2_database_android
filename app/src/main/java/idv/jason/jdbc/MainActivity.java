package idv.jason.jdbc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Connect> connectList = new ArrayList<>();
    private ConnectAdapter connectAdapter;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(onRefreshListener);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        connectAdapter = new ConnectAdapter(this, connectList);
        recyclerView.setAdapter(connectAdapter);

        loadData();
    }


    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Connection connection = connect();
                if (null != connection) {
                    connectList.clear();
                    connectList.addAll(readConnectTable(connection));

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            connectAdapter.notifyDataSetChanged();

                            if (refreshLayout.isRefreshing()) {
                                refreshLayout.setRefreshing(false);
                            }

                            if (connectList.isEmpty()) {
                                Toast.makeText(MainActivity.this, "查詢無資料", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (refreshLayout.isRefreshing()) {
                                refreshLayout.setRefreshing(false);
                            }

                            Toast.makeText(MainActivity.this, "與資料伺服器連線失敗！！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }


    private Connection connect() {
        String driverName = "org.h2.Driver";
        String dbURL = "jdbc:h2:tcp://172.16.129.13/~/test";
        String userName = "sa";
        String userPwd = "";
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(dbURL, userName, userPwd);
        } catch (Exception e) {
            return null;
        }
    }

    private List<Connect> readConnectTable(Connection connection) {
        List<Connect> connectList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM CONNECT";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                connectList.add(new Connect(rs.getInt("id"), rs.getTimestamp("time"), rs.getString("user"), rs.getString("ip")));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {

                }
        }

        return connectList;
    }


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();
        }
    };

}
