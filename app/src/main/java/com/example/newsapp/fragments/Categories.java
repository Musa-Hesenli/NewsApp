package com.example.newsapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;

import com.example.newsapp.R;
import com.example.newsapp.ui.Category;
import com.example.newsapp.ui.NewsCategoriesAdatper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Categories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Categories extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Categories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Categories.
     */
    // TODO: Rename and change types and number of parameters
    public static Categories newInstance(String param1, String param2) {
        Categories fragment = new Categories();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoryRecycler);
        NewsCategoriesAdatper adatper = new NewsCategoriesAdatper(view.getContext());
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Politics", "https://wallpapercave.com/wp/wp3274901.jpg"));
        categories.add(new Category("Sports", "https://www.tsn.ca/polopoly_fs/1.1660928.1624826513!/fileimage/httpImage/image.jpg_gen/derivatives/landscape_980/belgium-celebrates.jpg"));
        categories.add(new Category("Technology", "https://www.exciteit.com.au/wp-content/uploads/2018/02/Technology-industry.jpg"));
        categories.add(new Category("Finance", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLkB84osUL-KNb0VlzjWqjSnSTCyD8WBTNumqXwC_JE4qDPgwtfXE3dmEUecG0UIpG2Jg&usqp=CAU"));
        categories.add(new Category("Jobs", "https://c4.wallpaperflare.com/wallpaper/707/220/899/gradient-blue-pink-abstract-art-wallpaper-preview.jpg"));
        categories.add(new Category("Health", "https://img.freepik.com/free-vector/dark-hexagonal-background-with-gradient-color_79603-1409.jpg?size=626&ext=jpg&ga=GA1.2.1478321850.1624233600"));
        categories.add(new Category("Business", "https://media.istockphoto.com/photos/lets-merge-our-talents-as-we-get-to-the-top-picture-id1008207382?k=6&m=1008207382&s=612x612&w=0&h=hWCJqGWeATT9HOH5m2T0Rlldfh-CNm-rxBbYNNCYQos="));
        categories.add(new Category("Books", "https://images.unsplash.com/photo-1589998059171-988d887df646?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8&w=1000&q=80"));
        categories.add(new Category("Euro 2020", "https://i1.wp.com/firsttimefinish.co.uk/wp-content/uploads/2021/05/ftf-euro-2020-guide-1.png?fit=2100%2C1500&ssl=1"));
        categories.add(new Category("Start-ups", "https://www.bbva.com/wp-content/uploads/2020/05/Startups_post_COVID19-1920x1180.png"));
        categories.add(new Category("Education", "https://i.stack.imgur.com/sP7nK.png"));
        categories.add(new Category("Movie", "https://media.istockphoto.com/videos/blue-neon-light-abstract-visual-geometry-motion-graphic-technology-video-id1179960209?s=640x640"));
        categories.add(new Category("Design", "https://media.istockphoto.com/videos/blue-neon-light-abstract-visual-geometry-motion-graphic-technology-video-id1179960209?s=640x640"));
        categories.add(new Category("Entertainment", "https://media.istockphoto.com/videos/blue-neon-light-abstract-visual-geometry-motion-graphic-technology-video-id1179960209?s=640x640"));
        categories.add(new Category("General", "https://media.istockphoto.com/videos/blue-neon-light-abstract-visual-geometry-motion-graphic-technology-video-id1179960209?s=640x640"));
        categories.add(new Category("Movie", "https://media.istockphoto.com/videos/blue-neon-light-abstract-visual-geometry-motion-graphic-technology-video-id1179960209?s=640x640"));
        categories.add(new Category("Health", "https://media.istockphoto.com/videos/blue-neon-light-abstract-visual-geometry-motion-graphic-technology-video-id1179960209?s=640x640"));
        categories.add(new Category("Science", "https://i.stack.imgur.com/sP7nK.png"));

        adatper.setCategories(categories);
        recyclerView.setAdapter(adatper);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        return view;
    }

}