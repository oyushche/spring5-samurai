package com.luxoft;

import com.luxoft.samurai.data.Activity;
import com.luxoft.samurai.data.Samurai;

import java.util.Arrays;
import java.util.List;

public class SamuraiGenerator
{
    private static int counter = 0;
    private static String names[] = {"Kurasava", "Nakamuro", "Tonukato", "Oleh"};

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
        return Arrays.asList(new Activity[]
                {
                        new Activity("Jump"),
                        new Activity("Kill"),
                        new Activity("Meditate")
                });
    }
}
