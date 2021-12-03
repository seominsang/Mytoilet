package com.fkeo2021.mytoilet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaceListRecyclerAdaper extends RecyclerView.Adapter {

    Context context; //운영체제능력
    //대량의 데이터 List (필요하니까)
    List<Place> places;

    //생성자 - 객체가 new될때 자동으로 실행되는 메소드 (알트 + 인서트키로 콘스트럭만들면됨)
    public PlaceListRecyclerAdaper(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context); //현수막 부풀려서 뷰를 만들어내는 기능 기능공(인플레이터)
        View itemView = inflater.inflate(R.layout.recycler_item_list_fragment, parent, false);
        VH vh= new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {       //holder가 현수막을 받았음 여기서 안에 내용바꾸는작업
        VH vh= (VH)holder;

        Place place= places.get(position);

        vh.tvPlaceName.setText(place.place_name);
        if(place.road_address_name.equals("")) vh.tvAddress.setText(place.address_name);  //주소없으면 지번으로
        else vh.tvAddress.setText(place.address_name);
        vh.tvDistance.setText(place.distance+"m");
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    //현수막 1개 객체(아이템뷰)를 참조하고 있는 클래스
    class VH extends RecyclerView.ViewHolder{

        TextView tvPlaceName;
        TextView tvAddress;
        TextView tvDistance;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvPlaceName= itemView.findViewById(R.id.tv_place_name);
            tvAddress= itemView.findViewById(R.id.tv_address);
            tvDistance= itemView.findViewById(R.id.tv_distance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //현재 클릭된 위치번호를 알수있음
                    int index= getAdapterPosition();


                    Intent intent= new Intent(context, PlaceUrlActivity.class);
                    intent.putExtra("place_url", places.get(index).place_url);
                    context.startActivity(intent);
                }
            });
        }
    }
}
