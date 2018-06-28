package com.sun.pillow1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

public class AdapterFriendList extends RecyclerView.Adapter<AdapterFriendList.MyViewHolder>{

    List<Friend> friend_list;
    Context context;







    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView friend_name, status;
        public ImageView display_pic;
        ImageView star;


        public  void swapItems(List< Friend > todolist){
            friend_list = todolist;
            notifyDataSetChanged();
        }


        public MyViewHolder(View view) {
            super(view);
            context=view.getContext();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("callpostion", getLayoutPosition() + "");
                    String name=friend_list.get(getLayoutPosition()).getName();
                   OutputStream out=Streamer.getInstance().getOutputStreamer();
                    try {
                        out.write(("call:" + name).getBytes());
                       Intent in=new Intent(context,Call_picked.class);
                        in.putExtra("text","Calling "+name+".....");
                        in.putExtra("state","call");
                       context.startActivity(in);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //display_pic = (ImageView) itemView.findViewById(R.id.imageView);
            friend_name = (TextView) itemView.findViewById(R.id.friend_name);
            status = (TextView) itemView.findViewById(R.id.status);
              star= (ImageView) itemView.findViewById(R.id.fav);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String user = "";
                    int fav_no = -1;
                    if (friend_list.get(getLayoutPosition()).getFav() == 0) {
                        if (friend_list.get(getLayoutPosition()).getFavCount() == 0) {
                            Toast.makeText(context, "You can add only 3 contacts in speed dial", Toast.LENGTH_SHORT).show();
                        }

                        if (friend_list.get(getLayoutPosition()).getFavCount() == 1) {
                            user = friend_list.get(getLayoutPosition()).getName();
                            fav_no = 1;
                            Log.i("starcount", 1 + "");
                            star.setImageResource(R.drawable.star1);
                            friend_list.get(getLayoutPosition()).setFav(1, friend_list.get(getLayoutPosition()).getFav());
                        } else if (friend_list.get(getLayoutPosition()).getFavCount() == 2) {
                            user = friend_list.get(getLayoutPosition()).getName();
                            fav_no = 2;
                            star.setImageResource(R.drawable.star2);
                            friend_list.get(getLayoutPosition()).setFav(2, friend_list.get(getLayoutPosition()).getFav());
                            Log.i("starcount", 2 + "");
                        } else if (friend_list.get(getLayoutPosition()).getFavCount() == 3) {
                            user = friend_list.get(getLayoutPosition()).getName();
                            fav_no = 3;
                            star.setImageResource(R.drawable.star3);
                            friend_list.get(getLayoutPosition()).setFav(3, friend_list.get(getLayoutPosition()).getFav());
                            Log.i("starcount", 3 + "");
                        }


                    } else {
                        user = friend_list.get(getLayoutPosition()).getName();
                        fav_no = 0;
                        star.setImageResource(R.drawable.star);
                        friend_list.get(getLayoutPosition()).setFav(0, friend_list.get(getLayoutPosition()).getFav());

                    }
                    if (fav_no != -1) {
                        String json_string = "{'user1':'shubham','num':" + fav_no + ",'user2':'" + user + "'}";
                        Log.i("json_string", json_string);
                        JsonObjectRequest jsonObjectRequest = null;
                        String url="http://13.126.45.185:666/favourites/insert";
                        try {
                             jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_string), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                        } catch (JSONException e) {
                            Log.i("json_error",e.toString());
                        }
                        RequestQueue queue=Volley.newRequestQueue(context);
                        queue.add(jsonObjectRequest);

                    }
                }
            });
        }
    }



        public AdapterFriendList(List<Friend> friend_list) {
            Log.i("hello","helllo");
            this.friend_list = friend_list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.friendlist, parent, false);

            return new MyViewHolder(itemView);
        }




        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Friend friend = friend_list.get(position);
            holder.friend_name.setText(friend.getName());
            holder.status.setText(friend.getisOnline());
            if(friend.getFav()==0)
            {
                holder.star.setImageResource(R.drawable.star);

            }
            if(friend.getFav()==1)
            {
                holder.star.setImageResource(R.drawable.star1);

            }
            if(friend.getFav()==2)
            {
                holder.star.setImageResource(R.drawable.star2);

            }
            if(friend.getFav()==3)
            {
                holder.star.setImageResource(R.drawable.star3);

            }
        }


        @Override
        public int getItemCount() {
            return friend_list.size();
        }
}

