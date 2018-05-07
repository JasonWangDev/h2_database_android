package idv.jason.jdbc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ConnectAdapter extends RecyclerView.Adapter<ConnectAdapter.ViewHolder> {

    private Context context;
    private List<Connect> connectList;


    public ConnectAdapter(Context context, List<Connect> connectList) {
        this.context = context;
        this.connectList = connectList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_connect, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Connect connect = connectList.get(position);
        if (null != connect) {
            holder.tvUser.setText(connect.getUser());
            holder.tvIp.setText(connect.getIp());
            holder.tvDateTime.setText(String.format("%tF %<tT", connect.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return null != connectList ? connectList.size() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUser;
        private TextView tvIp;
        private TextView tvDateTime;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUser = itemView.findViewById(R.id.tv_User);
            tvIp = itemView.findViewById(R.id.tv_Ip);
            tvDateTime = itemView.findViewById(R.id.tv_DateTime);
        }
    }

}
