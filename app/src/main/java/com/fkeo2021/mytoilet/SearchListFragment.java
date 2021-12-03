package com.fkeo2021.mytoilet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class SearchListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_list, container, false);
    }

    RecyclerView recyclerView;
    PlaceListRecyclerAdaper placeListRecyclerAdaper;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.recyclertview);

        //MainActivity의 Place데이터들을 가져오기 위해 MainActivity를 참조
        MainActivity ma= (MainActivity) getActivity(); //메인엑티비티 맞다고 앞에서 써주는거임
        if(ma.searchLocalApiResponse==null) return;     //데이터를 아직 안받았으면 만들지 말라는 뜻 리턴은 그 밑에 하지말라는얘기

        placeListRecyclerAdaper= new PlaceListRecyclerAdaper(getActivity(), ma.searchLocalApiResponse.documents);
        recyclerView.setAdapter(placeListRecyclerAdaper); //어댑터를 플레이스리스트리사이클뷰에 연결하는거임

    }
}
