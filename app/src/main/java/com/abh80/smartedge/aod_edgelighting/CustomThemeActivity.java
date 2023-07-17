package com.abh80.smartedge.aod_edgelighting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abh80.smartedge.R;
import com.abh80.smartedge.aod_edgelighting.adapter.CustomColorAdapter;
import com.abh80.smartedge.aod_edgelighting.adapter.CustomThemeAdapter;
import com.abh80.smartedge.aod_edgelighting.edge.Drawable_vector;
import com.abh80.smartedge.aod_edgelighting.edge.MyItem;
import com.abh80.smartedge.aod_edgelighting.edge.MyPreview;
import com.abh80.smartedge.aod_edgelighting.edge.MyShare;
import com.abh80.smartedge.aod_edgelighting.edge.OtherUtils;
import com.abh80.smartedge.aod_edgelighting.item.ItemIcon;
import com.abh80.smartedge.aod_edgelighting.item.ItemPaths;
import com.abh80.smartedge.aod_edgelighting.model.CustomImageModel;
import com.abh80.smartedge.aod_edgelighting.model.DefaultThemeModel;
import com.abh80.smartedge.aod_edgelighting.service.DrawServiceApply;
import com.abh80.smartedge.aod_edgelighting.service.DrawServicePreview;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


import java.util.ArrayList;


public class CustomThemeActivity extends AppCompatActivity implements View.OnClickListener {
    public String appId, interId, appOpenId, nativeId;
    public SharedPref mSharedPref;
    RecyclerView rv_drawable, rv_select_color;
    ArrayList<CustomImageModel> arrayList;
    ArrayList<DefaultThemeModel> arrayList_color_2, list_3, list_4, list;
    CustomThemeAdapter adapter;
    CustomColorAdapter adapter_color;
    RadioButton rb_multi_color, rb_2_color, rb_3_color, rb_4_color, rb_1_color, rb_custom_color;
    RadioGroup rg_first;
    RadioGroup rg_second;
    String checkRB;
    Intent service_preview, service_apply;
    ItemIcon itemIcon;
    float dimension = 10;
    ImageView iv_selectedImage;
    SeekBar sb_border_size, sb_speed;
    TextView tv_speed, tv_border;
    int borderSizeProgress;
    int singleColorSelected = Color.parseColor("#5900bd");
    Button civ_1, civ_2, civ_3, civ_4, civ_5;
    LinearLayout linear_multi_color;
    int color1 = Color.parseColor("#5900bd");
    int color2 = Color.parseColor("#ac0018");
    int color3 = Color.parseColor("#ff9000");
    int color4 = Color.parseColor("#00f0ff");
    int color5 = Color.parseColor("#000000");
    SharedPreferences.Editor shareEditor;
    SharedPreferences shareGet;
    ArrayList<MyItem> defaultTheme1;
    int speedAnimationProgress;
    String selectTheme;
    String itemCheck;
    int save_speed, save_border_size;
    ImageView iv_back;
    int count;
    //switch
    LabeledSwitch labeledSwitch;
    TextView textViewEnabled;

    private FrameLayout frameLayoutContainer;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_theme);


        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        iv_back = findViewById(R.id.iv_back);
        labeledSwitch = findViewById(R.id.edgeSwitch);
        textViewEnabled = findViewById(R.id.tvEnable);
        rv_drawable = findViewById(R.id.rv_custom_theme);
        rv_select_color = findViewById(R.id.rv_select_color);
        rb_multi_color = findViewById(R.id.rb_multi_color);
        rb_1_color = findViewById(R.id.rb_single_color);
        rb_2_color = findViewById(R.id.rb_2_color);
        rb_3_color = findViewById(R.id.rb_3_color);
        rb_4_color = findViewById(R.id.rb_4_color);
        rb_custom_color = findViewById(R.id.rb_custom_color);
        rg_first = findViewById(R.id.rg_first);
        rg_second = findViewById(R.id.rg_last);


        sb_border_size = findViewById(R.id.sb_border_size);
        sb_speed = findViewById(R.id.seekBar_speed);
        tv_speed = findViewById(R.id.tv_speed);
        tv_border = findViewById(R.id.tv_border);

        civ_1 = findViewById(R.id.civ_1);
        civ_2 = findViewById(R.id.civ_2);
        civ_3 = findViewById(R.id.civ_3);
        civ_4 = findViewById(R.id.civ_4);
        civ_5 = findViewById(R.id.civ_5);
        linear_multi_color = findViewById(R.id.linear_multi_color);

        civ_1.setOnClickListener(this);
        civ_2.setOnClickListener(this);
        civ_3.setOnClickListener(this);
        civ_4.setOnClickListener(this);
        civ_5.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        textViewEnabled.setText("Enabled EdgeLight");
        labeledSwitch.setOn(false);



        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getResources().getString(R.string.admobBanner));


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        service_preview = new Intent(CustomThemeActivity.this, DrawServicePreview.class);
        service_apply = new Intent(CustomThemeActivity.this, DrawServiceApply.class);

        stopService(service_preview);


        shareEditor = getSharedPreferences("selectTheme", MODE_PRIVATE).edit();
        shareGet = getSharedPreferences("selectTheme", MODE_PRIVATE);

        save_speed = shareGet.getInt("speed", 11);
        speedAnimationProgress = save_speed;
        sb_speed.setProgress(save_speed);
        save_border_size = shareGet.getInt("border_size", 10);
        borderSizeProgress = save_border_size;
        sb_border_size.setProgress(save_border_size);


        mSharedPref = new SharedPref(this);
        getAdIdFromPref(this);


        getDefaultRB();
        getSpeed();
        getBorderSpeed();
        initAd();


        rg_first.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId != -1)
                    rg_second.check(-1);
                switch (checkedId) {
                    case R.id.rb_single_color:
                        radioButtonNumberPref("1");
                        colorRecyclerView("1");
                        break;
                    case R.id.rb_2_color:
                        radioButtonNumberPref("2");
                        colorRecyclerView("2");
                        break;
                    case R.id.rb_3_color:
                        radioButtonNumberPref("3");
                        colorRecyclerView("3");
                        break;
                }
            }
        });

        rg_second.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId != -1)
                    rg_first.check(-1);
                switch (checkedId) {
                    case R.id.rb_4_color:
                        radioButtonNumberPref("4");
                        colorRecyclerView("4");
                        break;
                    case R.id.rb_custom_color:
                        radioButtonNumberPref("custom_color");
                        colorRecyclerView("custom_color");
                        break;
                    case R.id.rb_multi_color:
                        radioButtonNumberPref("multi_color");
                        colorRecyclerView("multi_color");
                        break;
                }
            }
        });

        list = new ArrayList<>();
        rv_drawable.setLayoutManager(new GridLayoutManager(this, 5));
        arrayList = new ArrayList<>();
        arrayList.add(new CustomImageModel("Theme 1", R.drawable.minus, R.drawable.minus_selected));
        arrayList.add(new CustomImageModel("Theme 2", R.drawable.snow_flack, R.drawable.snow_flack_selected));
        arrayList.add(new CustomImageModel("Theme 3", R.drawable.plus, R.drawable.plus_selected));
        arrayList.add(new CustomImageModel("Theme 4", R.drawable.circle, R.drawable.circle_selected));
        arrayList.add(new CustomImageModel("Theme 5", R.drawable.smile, R.drawable.smile_selected));
        arrayList.add(new CustomImageModel("Theme 6", R.drawable.heart, R.drawable.heart_selected));
        arrayList.add(new CustomImageModel("Theme 7", R.drawable.sun, R.drawable.sun_selected));
        arrayList.add(new CustomImageModel("Theme 8", R.drawable.star, R.drawable.star_selected));
        arrayList.add(new CustomImageModel("Theme 9", R.drawable.clude, R.drawable.clude_selected));
        arrayList.add(new CustomImageModel("Theme 10", R.drawable.moon, R.drawable.moon_selected));

        adapter = new CustomThemeAdapter(this, arrayList, new CustomThemeAdapter.ClickedLister() {
            @Override
            public void onClicked(int position, ImageView imageView) {
                count++;

                Drawable_vector drawable_vector = new Drawable_vector();
                ArrayList<ItemPaths> cloud = drawable_vector.getCloud();
                ArrayList<ItemPaths> emoji_smile = drawable_vector.getEmoji_smile();
                ArrayList<ItemPaths> moon = drawable_vector.getMoon();
                ArrayList<ItemPaths> circle = drawable_vector.getCircle();
                ArrayList<ItemPaths> plus = drawable_vector.getPlus();
                ArrayList<ItemPaths> star = drawable_vector.getStar();
                ArrayList<ItemPaths> love = drawable_vector.getLove();
                ArrayList<ItemPaths> square = drawable_vector.getSquare();
                ArrayList<ItemPaths> snowFlack = drawable_vector.getSnowFlack();
                ArrayList<ItemPaths> sun = drawable_vector.getSun();

                switch (position) {
                    case 0:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", null, 100);
                        dimension = 17;
                        shareEditor.putString("selectItemIcon", "null_itemIcon");
                        shareEditor.apply();
                        break;
                    case 1:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", snowFlack, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 2:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", plus, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 3:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", circle, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 4:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", emoji_smile, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 5:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", love, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 6:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", sun, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 7:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", star, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 8:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", cloud, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");

                        break;
                    case 9:
                        stopService(service_preview);
                        itemIcon = new ItemIcon("plus", moon, 100);
                        dimension = 30;
                        shareEditor.putString("selectItemIcon", "icon");
                        break;

                }

                if (borderSizeProgress <= 12) {
                    borderSizeProgress = 20;
                }

                ArrayList<MyItem> defaultTheme1;
                if (position == 0) {
                    defaultTheme1 = OtherUtils.makeThemeDrawableColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
                } else {
                    defaultTheme1 = OtherUtils.makeThemeDrawableColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
                }
                MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
                preview1.setMyItem(defaultTheme1.get(0));
                MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                startService(service_preview);

                shareEditor.putString("selectTheme", "drawable");
                shareEditor.apply();
            }
        });
        rv_drawable.setAdapter(adapter);


        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {

                    getSwitchOn(isOn);

                //getSwitchOn(isOn);

            }
        });


    }


    private void getSwitchOn(boolean isOn) {
        if (isOn) {
            textViewEnabled.setText("Enabled EdgeLight");
            startService(service_preview);
        } else {
            textViewEnabled.setText("Disable EdgeLight");
            stopService(service_preview);
        }
    }


    private void initAd() {

    }

    public void radioButtonNumberPref(String number) {
        MyShare.customThemeNumber(CustomThemeActivity.this, number);
    }

    public void getDefaultRB() {
        checkRB = MyShare.getStr(this);
        switch (checkRB) {
            case "multi_color":
                rb_multi_color.setChecked(true);
                rv_select_color.setVisibility(View.GONE);
                colorRecyclerView("multi_color");
                break;
            case "1":
                rb_1_color.setChecked(true);
                rv_select_color.setVisibility(View.GONE);
                break;
            case "2":
                rb_2_color.setChecked(true);
                rv_select_color.setVisibility(View.VISIBLE);
                colorRecyclerView("2");
                break;
            case "3":
                rb_3_color.setChecked(true);
                rv_select_color.setVisibility(View.VISIBLE);
                colorRecyclerView("3");
                break;
            case "4":
                rb_4_color.setChecked(true);
                rv_select_color.setVisibility(View.VISIBLE);
                colorRecyclerView("4");
                break;
            case "custom_color":
                rb_custom_color.setChecked(true);
                rv_select_color.setVisibility(View.GONE);
                linear_multi_color.setVisibility(View.GONE);
                break;

        }
    }

    public void colorRecyclerView(String name) {
        //Color RecyclerView
        arrayList_color_2 = new ArrayList<>();
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color1, Color.parseColor("#ff0000"), Color.parseColor("#00f0ff")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color2, Color.parseColor("#a200ff"), Color.parseColor("#fcff00")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color3, Color.parseColor("#00ff36"), Color.parseColor("#0018ff")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color4, Color.parseColor("#ff0000"), Color.parseColor("#ffffff")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color5, Color.parseColor("#00f0ff"), Color.parseColor("#d8ff00")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color6, Color.parseColor("#c600ff"), Color.parseColor("#480098")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color7, Color.parseColor("#0600ff"), Color.parseColor("#ff0000")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color8, Color.parseColor("#ff9bfe"), Color.parseColor("#7d0808")));
        arrayList_color_2.add(new DefaultThemeModel("2", R.drawable.color9, Color.parseColor("#ff0000"), Color.parseColor("#f0ff00")));
        //Color 3 List
        list_3 = new ArrayList<>();
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_1, Color.parseColor("#00d2ff"), Color.parseColor("#ff00fc"), Color.parseColor("#f0ff00")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_2, Color.parseColor("#ff0084"), Color.parseColor("#00ff1e"), Color.parseColor("#6b03cc")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_3, Color.parseColor("#00ccff"), Color.parseColor("#ffde00"), Color.parseColor("#cc0387")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_4, Color.parseColor("#00d2ff"), Color.parseColor("#ae00ff"), Color.parseColor("#cc032e")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_5, Color.parseColor("#0000ff"), Color.parseColor("#00baff"), Color.parseColor("#ffffff")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_6, Color.parseColor("#24ff00"), Color.parseColor("#fff000"), Color.parseColor("#ff0000")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_7, Color.parseColor("#00d3bd"), Color.parseColor("#ffc600"), Color.parseColor("#ff0078")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_8, Color.parseColor("#e4ff00"), Color.parseColor("#7d00ac"), Color.parseColor("#00a8ff")));
        list_3.add(new DefaultThemeModel("3", R.drawable.color3_9, Color.parseColor("#ff0000"), Color.parseColor("#00ac08"), Color.parseColor("#0006ff")));
        //Color 4 List
        list_4 = new ArrayList<>();
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_1, Color.parseColor("#5900bd"), Color.parseColor("#ac0018"), Color.parseColor("#ff9000"), Color.parseColor("#00f0ff")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_2, Color.parseColor("#ffcc00"), Color.parseColor("#ff0000"), Color.parseColor("#6d00ac"), Color.parseColor("#ffffff")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_3, Color.parseColor("#ffffff"), Color.parseColor("#1e00ff"), Color.parseColor("#ac0000"), Color.parseColor("#000000")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_4, Color.parseColor("#de00ff"), Color.parseColor("#1e00ff"), Color.parseColor("#ffd800"), Color.parseColor("#1eff00")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_5, Color.parseColor("#ffffff"), Color.parseColor("#00ccff"), Color.parseColor("#0600ff"), Color.parseColor("#ff00d2")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_6, Color.parseColor("#00ccff"), Color.parseColor("#000000"), Color.parseColor("#ff00d2"), Color.parseColor("#0600ff")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_7, Color.parseColor("#fc00ff"), Color.parseColor("#f5fe00"), Color.parseColor("#ff0000"), Color.parseColor("#0cff00")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_8, Color.parseColor("#0600ff"), Color.parseColor("#00e9fe"), Color.parseColor("#00ff00"), Color.parseColor("#ffd800")));
        list_4.add(new DefaultThemeModel("4", R.drawable.color4_9, Color.parseColor("#00e4ff"), Color.parseColor("#00ff00"), Color.parseColor("#fe00f5"), Color.parseColor("#ffd800")));
        if (name.equals("1")) {
            rv_select_color.setVisibility(View.GONE);
            linear_multi_color.setVisibility(View.GONE);
            getSingleColor("single");
        }
        if (name.equals("2")) {
            rv_select_color.setVisibility(View.VISIBLE);
            linear_multi_color.setVisibility(View.GONE);
            list = arrayList_color_2;
        } else if (name.equals("3")) {
            rv_select_color.setVisibility(View.VISIBLE);
            linear_multi_color.setVisibility(View.GONE);
            list = list_3;
        } else if (name.equals("4")) {
            rv_select_color.setVisibility(View.VISIBLE);
            linear_multi_color.setVisibility(View.GONE);
            list = list_4;
        } else if (name.equals("custom_color")) {
            rv_select_color.setVisibility(View.GONE);
            linear_multi_color.setVisibility(View.GONE);
        } else if (name.equals("multi_color")) {
            rv_select_color.setVisibility(View.GONE);
            linear_multi_color.setVisibility(View.VISIBLE);
        }
        rv_select_color.setLayoutManager(new GridLayoutManager(this, 3));
        adapter_color = new CustomColorAdapter(this, list, new CustomColorAdapter.ClickedLister() {
            @Override
            public void onClicked(int position, DefaultThemeModel model) {
                count++;

                if (model.getName().equals("2")) {
                    stopService(service_preview);
                    if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                        defaultTheme1 = OtherUtils.makeThemeCustom2(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, model.getColor1(), model.getColor2());
                    } else {
                        defaultTheme1 = OtherUtils.makeThemeCustom2(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, model.getColor1(), model.getColor2());
                    }
                    MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
                    preview1.setMyItem(defaultTheme1.get(0));
                    MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                    startService(service_preview);
                    color1 = model.getColor1();
                    color2 = model.getColor2();

                    shareEditor.putString("selectTheme", "2");
                    shareEditor.apply();
                }
                if (model.getName().equals("3")) {
                    stopService(service_preview);
                    if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                        defaultTheme1 = OtherUtils.makeThemeCustom3(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, model.getColor1(), model.getColor2(), model.getColor3());
                    } else {
                        defaultTheme1 = OtherUtils.makeThemeCustom3(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, model.getColor1(), model.getColor2(), model.getColor3());
                    }

                    MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
                    preview1.setMyItem(defaultTheme1.get(0));
                    MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                    startService(service_preview);
                    color1 = model.getColor1();
                    color2 = model.getColor2();
                    color3 = model.getColor3();
                    shareEditor.putString("selectTheme", "3");
                    shareEditor.apply();
                }
                if (model.getName().equals("4")) {
                    stopService(service_preview);
                    if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                        defaultTheme1 = OtherUtils.makeThemeCustom4(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, model.getColor1(), model.getColor2(), model.getColor3(), model.getColor4());
                    } else {
                        defaultTheme1 = OtherUtils.makeThemeCustom4(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, model.getColor1(), model.getColor2(), model.getColor3(), model.getColor4());
                    }
                    MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
                    preview1.setMyItem(defaultTheme1.get(0));
                    MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                    startService(service_preview);
                    color1 = model.getColor1();
                    color2 = model.getColor2();
                    color3 = model.getColor3();
                    color4 = model.getColor4();
                    shareEditor.putString("selectTheme", "4");
                    shareEditor.apply();
                }
            }
        });
        rv_select_color.setAdapter(adapter_color);
    }

    public void getSpeed() {
        sb_speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_speed.setText("" + progress + " %");
                shareEditor.putInt("speed", progress);
                shareEditor.commit();
                speedAnimationProgress = progress;
                getProgressBarUpdated();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void getBorderSpeed() {
        sb_border_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_border.setText("" + progress + " %");
                shareEditor.putInt("border_size", progress);
                shareEditor.commit();
                borderSizeProgress = progress;
                getProgressBarUpdated();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void getProgressBarUpdated() {
        selectTheme = shareGet.getString("selectTheme", "default");
        itemCheck = shareGet.getString("selectItemIcon", "default");

        stopService(service_preview);
        if (selectTheme.equals("drawable")) {
            if (borderSizeProgress <= 9) {
                borderSizeProgress = 10;
            }
            ArrayList<MyItem> defaultTheme1;
            if (itemCheck.equals("null_itemIcon")) {


                defaultTheme1 = OtherUtils.makeThemeDrawableColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
            } else {

                defaultTheme1 = OtherUtils.makeThemeDrawableColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
            }
            MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
            preview1.setMyItem(defaultTheme1.get(0));
            MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
        } else if (selectTheme.equals("multi")) {
            if (itemCheck.equals("null_itemIcon")) {
                defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
            } else {
                defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
            }
            MyPreview single5 = new MyPreview(CustomThemeActivity.this);
            single5.setMyItem(defaultTheme1.get(0));
            MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
        } else if (selectTheme.equals("single")) {
            if (itemCheck.equals("null_itemIcon")) {
                defaultTheme1 = OtherUtils.makeThemeSingle(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, singleColorSelected, singleColorSelected);
            } else {
                defaultTheme1 = OtherUtils.makeThemeSingle(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, singleColorSelected, singleColorSelected);
            }
            MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
            preview1.setMyItem(defaultTheme1.get(0));
            MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));

        } else if (selectTheme.equals("2")) {
            if (itemCheck.equals("null_itemIcon")) {
                defaultTheme1 = OtherUtils.makeThemeCustom2(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2);
            } else {
                defaultTheme1 = OtherUtils.makeThemeCustom2(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2);
            }
            MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
            preview1.setMyItem(defaultTheme1.get(0));
            MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
        } else if (selectTheme.equals("3")) {
            stopService(service_preview);
            if (itemCheck.equals("null_itemIcon")) {
                defaultTheme1 = OtherUtils.makeThemeCustom3(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3);
            } else {
                defaultTheme1 = OtherUtils.makeThemeCustom3(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3);
            }

            MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
            preview1.setMyItem(defaultTheme1.get(0));
            MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
        } else if (selectTheme.equals("4")) {
            if (itemCheck.equals("null_itemIcon")) {
                defaultTheme1 = OtherUtils.makeThemeCustom4(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4);
            } else {
                defaultTheme1 = OtherUtils.makeThemeCustom4(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4);
            }
            MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
            preview1.setMyItem(defaultTheme1.get(0));
            MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
        }
        startService(service_preview);
    }

    public void getSingleColor(String bg_color) {
        ColorPickerDialogBuilder
                .with(CustomThemeActivity.this)
                .setTitle("Choose color")
                .initialColor(Color.RED)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        singleColorSelected = selectedColor;
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        singleColorSelected = selectedColor;
                        switch (bg_color) {
                            case "single":
                                stopService(service_preview);
                                if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                                    defaultTheme1 = OtherUtils.makeThemeSingle(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, singleColorSelected, singleColorSelected);
                                } else {
                                    defaultTheme1 = OtherUtils.makeThemeSingle(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, singleColorSelected, singleColorSelected);
                                }
                                MyPreview preview1 = new MyPreview(CustomThemeActivity.this);
                                preview1.setMyItem(defaultTheme1.get(0));
                                MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                                startService(service_preview);
                                shareEditor.putString("selectTheme", "single");
                                shareEditor.apply();


                                break;
                            case "1":
                                color1 = selectedColor;
                                civ_1.setBackgroundColor(color1);
                                stopService(service_preview);
                                if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
                                } else {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
                                }
                                MyPreview single = new MyPreview(CustomThemeActivity.this);
                                single.setMyItem(defaultTheme1.get(0));
                                MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                                startService(service_preview);
                                break;
                            case "2":
                                color2 = selectedColor;
                                civ_2.setBackgroundColor(color2);
                                stopService(service_preview);

                                if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
                                } else {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
                                }
                                MyPreview single2 = new MyPreview(CustomThemeActivity.this);
                                single2.setMyItem(defaultTheme1.get(0));
                                MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                                startService(service_preview);
                                break;
                            case "3":
                                color3 = selectedColor;
                                civ_3.setBackgroundColor(color3);
                                stopService(service_preview);
                                if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
                                } else {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
                                }
                                MyPreview single3 = new MyPreview(CustomThemeActivity.this);
                                single3.setMyItem(defaultTheme1.get(0));
                                MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                                startService(service_preview);
                                break;
                            case "4":
                                color4 = selectedColor;
                                civ_4.setBackgroundColor(color4);

                                if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
                                } else {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
                                }

                                stopService(service_preview);
                                MyPreview single4 = new MyPreview(CustomThemeActivity.this);
                                single4.setMyItem(defaultTheme1.get(0));
                                MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                                startService(service_preview);
                                break;
                            case "5":
                                color5 = selectedColor;
                                civ_5.setBackgroundColor(color5);
                                stopService(service_preview);

                                if (shareGet.getString("selectItemIcon", "default").equals("null_itemIcon")) {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, null, speedAnimationProgress, color1, color2, color3, color4, color5);
                                } else {
                                    defaultTheme1 = OtherUtils.makeThemeMultiColor(CustomThemeActivity.this, borderSizeProgress, itemIcon, speedAnimationProgress, color1, color2, color3, color4, color5);
                                }
                                MyPreview single5 = new MyPreview(CustomThemeActivity.this);
                                single5.setMyItem(defaultTheme1.get(0));
                                MyShare.putThemeUse(CustomThemeActivity.this, defaultTheme1.get(0));
                                startService(service_preview);
                                break;
                        }


                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_1:
                getSingleColor("1");
                shareEditor.putString("selectTheme", "multi");
                shareEditor.apply();
                break;
            case R.id.civ_2:
                getSingleColor("2");
                shareEditor.putString("selectTheme", "multi");
                shareEditor.apply();
                break;
            case R.id.civ_3:
                getSingleColor("3");
                shareEditor.putString("selectTheme", "multi");
                shareEditor.apply();
                break;
            case R.id.civ_4:
                getSingleColor("4");
                shareEditor.putString("selectTheme", "multi");
                shareEditor.apply();
                break;
            case R.id.civ_5:
                getSingleColor("5");
                shareEditor.putString("selectTheme", "multi");
                shareEditor.apply();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*stopService(service_preview);*/
    }

    private void getAdIdFromPref(Activity context) {
        //Test Ids
        /*appOpenId = context.getString(R.string.open_id);
        nativeId = context.getString(R.string.native_id);
        interId = context.getString(R.string.interstitial_id);*/
        //Real Ids
//        appOpenId = mSharedPref.getAppOpenId("appOpenId");
//        nativeId = mSharedPref.getNativeId("nativeId");
//        interId = mSharedPref.getInterId("interId");
    }

}