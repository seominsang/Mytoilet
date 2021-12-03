package com.fkeo2021.mytoilet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchMapFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //본사가 서포터매니저로 있기때문에 나머지 프레그먼트는 다 차일드(자식)매니저
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //맵은 데이터가 커서 오래걸리니까 맵 다운로드 되는동안 별개로 앱을 실행시키라는 명령어
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //괄호안에 메인액티비티는 지가 메인엑티비티인지 모르니까 써주는거임 바보라서
                MainActivity ma = (MainActivity) getActivity();

                //구글지도 위도/경도 객체
                LatLng me = new LatLng(ma.mylocation.getLatitude(), ma.mylocation.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 16));

                //구글 맵에 내 위치 표시 설정
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                
                //몇 가지 지도 설정
                //줌 설정
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);

                //반복문 돌리기
                for(Place place : ma.searchLocalApiResponse.documents){
                    double latitude= Double.parseDouble(place.y);
                    double longitude= Double.parseDouble(place.x);
                    LatLng position= new LatLng(latitude, longitude);
                    
                    //마커옵션 객체를 통해 마커의 설정들
                    MarkerOptions options= new MarkerOptions().position(position).title(place.place_name).snippet(place.distance+"m");
                    googleMap.addMarker(options).setTag(place.place_url);
                }

                //구글 맵에게 마커의 정보창(infoWindow)을 클릭하는 것에 반응 시키기
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                       // Toast.makeText(ma, ""+marker.getTag().toString(), Toast.LENGTH_SHORT).show();
                       // 장소 Url의 웹 문서를 사용자에게 보여주기 위해 새로운 액티비티로 전환
                       
                       //액티비티전환은 택배기사가 필요함 즉 새로운화면보려면 intent가 있어야됨
                        Intent intent= new Intent(getActivity(), PlaceUrlActivity.class);
                        intent.putExtra("place_url", marker.getTag().toString()); //Place Url 정도 , 앞에 네임은 택배박스 라벨지임 택배박스 분류를하기 위해
                        startActivity(intent);
    
                    }
                });
                
            }
        });
    }
}
