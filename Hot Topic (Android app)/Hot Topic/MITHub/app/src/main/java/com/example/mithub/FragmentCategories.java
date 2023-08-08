package com.example.mithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentCategories extends Fragment {
    View view;
    Button animalCareButton, automotiveButton, businessButton, constructionButton, creativeArtsButton, digitalTechnologiesButton, educationButton, engineeringButton, foundationButton, hairdressingButton;
    Button healthButton, horticultureButton, hospitalityButton, languageButton, logisticsButton, maritimeButton, nursingButton, policeButton, socialServiceButton,sportsButton, supportLearningButton;

    public FragmentCategories() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.categories_fragment, container, false);
        animalCareButton = view.findViewById(R.id.animal_care_category_button);
        automotiveButton = view.findViewById(R.id.automotive_category_button);
        businessButton = view.findViewById(R.id.business_category_button);
        constructionButton = view.findViewById(R.id.construction_category_button);
        creativeArtsButton = view.findViewById(R.id.creative_arts_category_button);
        digitalTechnologiesButton = view.findViewById(R.id.digital_technologies_category_button);
        educationButton = view.findViewById(R.id.education_category_button);
        engineeringButton = view.findViewById(R.id.engineering_category_button);
        foundationButton = view.findViewById(R.id.foundation_category_button);
        hairdressingButton = view.findViewById(R.id.hairdressing_category_button);
        healthButton = view.findViewById(R.id.health_category_button);
        horticultureButton = view.findViewById(R.id.horticulture_category_button);
        hospitalityButton = view.findViewById(R.id.hospitality_category_button);
        languageButton = view.findViewById(R.id.language_category_button);
        logisticsButton = view.findViewById(R.id.logistics_category_button);
        maritimeButton = view.findViewById(R.id.maritime_category_button);
        nursingButton = view.findViewById(R.id.nursing_category_button);
        policeButton = view.findViewById(R.id.police_category_button);
        socialServiceButton = view.findViewById(R.id.social_service_category_button);
        sportsButton = view.findViewById(R.id.sports_category_button);
        supportLearningButton = view.findViewById(R.id.support_learning_category);


        animalCareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Animal Care"); }
        });
        automotiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Automotive"); }
        });
        businessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscussions("Business");
            }
        });
        constructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Construction"); }
        });
        creativeArtsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Creative Arts"); }
        });
        digitalTechnologiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Digital Technologies"); }
        });
        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Education"); }
        });
        engineeringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Engineering"); }
        });
        foundationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Foundation"); }
        });
        hairdressingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Hairdressing"); }
        });
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Health"); }
        });
        horticultureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Horticulture"); }
        });
        hospitalityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Hospitality"); }
        });
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Language"); }
        });
        logisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Logistics"); }
        });
        maritimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Maritime"); }
        });
        nursingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Nursing"); }
        });
        policeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Police"); }
        });
        socialServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Social Service"); }
        });
        sportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Sports"); }
        });
        supportLearningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDiscussions("Support Learning"); }
        });
        return view;

    }

    private void showDiscussions(String category) {
        Intent intent = new Intent(getContext(), CategoryDiscussionsActivity.class);
        intent.putExtra("Category", category);
        startActivity(intent);
    }
}
