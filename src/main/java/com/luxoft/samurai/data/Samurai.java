package com.luxoft.samurai.data;

import com.luxoft.SamuraiGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Samurai
{
    private long id;

    private String name;

    private int age;

    private List<Activity> activity;

    public List<Activity> addActivity(Activity activity)
    {
        this.activity.add(activity);

        return new ArrayList<>(this.activity);
    }

    public void setActivity(List<Activity> activity)
    {
        this.activity = new ArrayList<>(activity);
    }

    public Action doSomething()
    {
        return new Action(SamuraiGenerator.getRandomAction());
    }
}
