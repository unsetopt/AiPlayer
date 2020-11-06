package com.wildeyess.player.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.wildeyess.player.R;
import com.wildeyess.player.activity.PlayListActivity;
import com.wildeyess.player.base.BaseRvAdapter;
import com.wildeyess.player.base.BaseRvViewHolder;
import com.wildeyess.player.bean.VideoBean;
import com.wildeyess.player.view.IconFontTextView;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by wildeyess
 * create on 2020-10-20
 * description
 */
public class GridVideoAdapter extends BaseRvAdapter<VideoBean, GridVideoAdapter.GridVideoViewHolder> {

    public GridVideoAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(GridVideoViewHolder holder, VideoBean videoBean, int position) {
        holder.ivCover.setBackgroundResource(videoBean.getCoverRes());
        holder.tvContent.setText(videoBean.getContent());
        holder.tvDistance.setText(videoBean.getDistance() + " km");
        holder.ivHead.setImageResource(videoBean.getUserBean().getHead());

        holder.itemView.setOnClickListener(v -> {
            PlayListActivity.initPos = position;
            context.startActivity(new Intent(context, PlayListActivity.class));
        });
    }

    @NonNull
    @Override
    public GridVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gridvideo, parent, false);
        return new GridVideoViewHolder(view);
    }

    public class GridVideoViewHolder extends BaseRvViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_distance)
        IconFontTextView tvDistance;
        @BindView(R.id.iv_head)
        ImageView ivHead;

        public GridVideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
