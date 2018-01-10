package com.luxoft;

import com.luxoft.samurai.data.Activity;
import com.luxoft.samurai.data.Samurai;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SamuraiGenerator
{
    private static final Random RND = new Random();

    private static int counter = 0;
    private static String names[] = {"Kurasava", "Nakamuro", "Tonukato", "Oleh"};

    private static Activity[] activities = new Activity[]
            {
                    new Activity("Jump"),
                    new Activity("Kill"),
                    new Activity("Meditate")
            };

    public static Samurai generateSamurai()
    {
        Samurai samurai = new Samurai();

        samurai.setName(names[counter++]);
        samurai.setAge(counter + samurai.getName().length());

        samurai.setActivity(getActivityList());

        return samurai;
    }

    public static Samurai generateSamuraiWithIdAndName(long id, String name)
    {
        Samurai samurai = new Samurai();

        samurai.setId(id);
        samurai.setName(name);
        samurai.setAge(counter + samurai.getName().length());

        samurai.setActivity(getActivityList());
        return samurai;
    }

    private static List<Activity> getActivityList()
    {
        return Arrays.asList(activities);
    }

    public static String getRandomActivity()
    {
        return activities[RND.nextInt(activities.length - 1)].getName();
    }

}
