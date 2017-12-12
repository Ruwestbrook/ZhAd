package westbrook.com.zhad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by westbrook on 2017/12/12.
 * 自定义的ViewHolder
 */

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
 private Context context;

    Adapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position > 0 && position % 6 == 0) {
           holder.content.setVisibility(View.INVISIBLE);
           holder.title.setVisibility(View.INVISIBLE);
           holder.adImageView.setVisibility(View.VISIBLE);
        } else {
            holder.content.setVisibility(View.VISIBLE);
            holder.title.setVisibility(View.VISIBLE);
            holder.adImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdImageView adImageView;
        TextView title;
        TextView content;

        ViewHolder(View view){
            super(view);
            adImageView= (AdImageView) view.findViewById(R.id.id_iv_ad);
            title= (TextView) view.findViewById(R.id.id_tv_title);
            content= (TextView) view.findViewById(R.id.id_tv_desc);
        }
    }
}
