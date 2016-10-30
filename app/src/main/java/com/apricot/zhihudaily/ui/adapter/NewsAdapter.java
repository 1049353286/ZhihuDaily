package com.apricot.zhihudaily.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.apricot.zhihudaily.Constants;
import com.apricot.zhihudaily.MyApplication;
import com.apricot.zhihudaily.R;
import com.apricot.zhihudaily.bean.Story;
import com.apricot.zhihudaily.ui.activity.WebActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Apricot on 2016/6/7.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CardViewHolder>{
    private List<Story> storyList = new ArrayList<>();

    public NewsAdapter(List<Story> storyList){
        this.storyList =storyList;
    }

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.noimage)
            .showImageOnFail(R.drawable.noimage)
            .showImageForEmptyUri(R.drawable.lks_for_blank_url)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    @Override
    public CardViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item,parent,false);
        return new CardViewHolder(view, new CardViewHolder.ClickResponseListener() {
            @Override
            public void onWholeClick(int position) {
                Story story=storyList.get(position);
                WebActivity.StartWebActivity(MyApplication.getInstance(),Constants.Urls.EXTRA_URL+story.getId(),story.getTitle());
            }

            @Override
            public void onOverflowClick(View v, final int position) {
                PopupMenu popupMenu=new PopupMenu(parent.getContext(),v);
                MenuInflater inflater=popupMenu.getMenuInflater();
                inflater.inflate(R.menu.share_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_share_url:
                                shareStory(parent.getContext(),position);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void shareStory(Context context,int position){
        Story story= storyList.get(position);
        Intent share=new Intent(Intent.ACTION_SEND);
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, story.getTitle() + Constants.Strings.ZHIHU_QUESTION_LINK_PREFIX + story.getId());
        context.startActivity(Intent.createChooser(share,"分享"));
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Story story= storyList.get(position);
        imageLoader.displayImage(story.getImages().get(0), holder.storyImage, options);
        holder.storyTitle.setText(story.getTitle());

    }


    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public void setNewsList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public void updateNewsList(List<Story> storyList){
        setNewsList(storyList);
        notifyDataSetChanged();
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.thumbnail_image)
        ImageView storyImage;
        @Bind(R.id.story_title)
        TextView storyTitle;
        @Bind(R.id.share)
        ImageView overflow;
        private ClickResponseListener mClickResponseListener;

        public CardViewHolder(View itemView,ClickResponseListener clickResponseListener) {
            super(itemView);
            this.mClickResponseListener =clickResponseListener;
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            overflow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==overflow){
                mClickResponseListener.onOverflowClick(v,getAdapterPosition());
            }else{
                mClickResponseListener.onWholeClick(getAdapterPosition());
            }
        }


        public interface ClickResponseListener{
            void onWholeClick(int position);
            void onOverflowClick(View v,int position);
        }
    }
}
