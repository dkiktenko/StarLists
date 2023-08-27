package com.dkiktenko.starlists.NewList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StarList {

    private @Nullable String title;
    private @NonNull ArrayList<String> items;

    public StarList(@Nullable String title){
        this.title = title;
    }

    public void setTitle(@Nullable String title){
        this.title = title;
    }

    public void setItems(@NonNull ArrayList<String> items){
        this.items = items;
    }

    public @Nullable String getTitle(){
        return this.title;
    }

    public @NonNull ArrayList<String> getItems(){
        return this.items;
    }
}
