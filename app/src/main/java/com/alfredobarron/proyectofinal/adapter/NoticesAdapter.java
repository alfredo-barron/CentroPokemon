package com.alfredobarron.proyectofinal.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredobarron.proyectofinal.R;
import com.alfredobarron.proyectofinal.models.Notice;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;


public class NoticesAdapter extends BaseAdapter{

        private final Context context;
        private List<Notice> notices;

        public NoticesAdapter(Context context, List<Notice> notices) {
            this.context = context;
            this.notices = notices;
        }

        @Override
        public int getCount() {
            return notices.size();
        }

        @Override
        public Notice getItem(int position) {
            return notices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Notice notice = getItem(position);
            ViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                convertView = from(context).inflate(R.layout.notices_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }

            viewHolder.text1.setText(notice.getTrainer());

            Picasso.with(context).load(notice.getImage()).into(viewHolder.pokemonImage);

            viewHolder.text2.setText("Tu " + notice.getSpecie() + " esta " + notice.getStatus());
            return convertView;
        }

        class ViewHolder {

            @InjectView(R.id.icon)
            ImageView pokemonImage;

            @InjectView(R.id.text1)
            TextView text1;

            @InjectView(R.id.text2)
            TextView text2;

            public ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }

}
