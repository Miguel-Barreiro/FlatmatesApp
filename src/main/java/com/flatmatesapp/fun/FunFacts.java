package com.flatmatesapp.fun;

import com.flatmatesapp.util.FacesMessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author iulian.dafinoiu
 */
@Component
@Scope("singleton")
public class FunFacts implements Serializable {
    private static final long serialVersionUID = 176845493674583492L;
    
    private List<String> funFacts = new ArrayList<>();
    
    {
        funFacts.add("Did you know Foxes are members of the dog family?");
        funFacts.add("Did you know A female fox is called a “vixen”, a male fox is called a “dog fox” (like me)?");
        funFacts.add("Did you know Foxes have vertical pupils that look more like those of cats than the rounded pupils that other dogs have?");
        funFacts.add("Did you know Foxes are the most widespread species of wild dog in the world?");
        funFacts.add("Some cruel people continue to hunt foxes and ridiculously call it a “sport”. That makes me so MAD!");
        funFacts.add("Did you know A group of foxes is called a “skulk” or a “leash”?");
        funFacts.add("Did you know Foxes are the only type of dog capable of retracting their claws like cats do?");
        funFacts.add("Did you know Grey foxes who live in North America are the only type of dogs who can climb trees!?");
        funFacts.add("Did you know Foxes show great caring, adaptability and intelligence when raising their young?");
        funFacts.add("Did you know Foxes are monogamous? They usually mate for life. How about you?");
        funFacts.add("Did you know Foxes usually take on nannies to help with their pups?");
        funFacts.add("Did you know Foxes can identify each other's voices, just like humans?");
        funFacts.add("Did you know the red fox has 28 different sounds they use to communicate?");
        funFacts.add("Did you know Foxes can hear low-frequency sounds and rodents digging underground?");
        funFacts.add("Did you know the tip of the tail's fox is always white and the tips of their ears and feet are always black?");
        funFacts.add("Did you know Foxes mate once per year. Peak of the mating season is in January?");
        funFacts.add("Did you know a Fox's pregnancy lasts 53 days and it ends with 3 to 6 pups?");
        funFacts.add("Did you know Foxes live up to 3 years in the wild and up to 10 years in captivity? Me? I live forever!");
        funFacts.add("Did you know Foxes were the first type of dog that befriended a human?");
        funFacts.add("Did you know MadFox not only is the best app for updating envs but also teaches you about foxes?");
        funFacts.add("Did you know MadFox can schedule an artifact to be deployed whenever you want?");
        funFacts.add("Did you know MadFox can run any version of any artifact even if it's not on Piquette anymore?");
        funFacts.add("Did you know MadFox not only is the best app for updating envs but also teaches you about foxes?");
        funFacts.add("Did you know MadFox can take a snapshot of your env state and restore it later in time?");
        funFacts.add("The class Object inherits from Chuck Norris.");
        funFacts.add("Chuck Norris can unit test an entire application with a single assert.");
        funFacts.add("Chuck Norris writes code that optimizes itself.");
        funFacts.add("Chuck Norris can divide by zero.");
        funFacts.add("Chuck Norris never gets a syntax error. Instead, The language gets a DoesNotConformToChuck error.");
        funFacts.add("Chuck Norris can write multi-threaded applications with a single thread.");
        funFacts.add("Chuck Norris has only one Master - MadFox!");
        funFacts.add("Did you know Gamesys had over 4.3 billion cash bets during 2012?");
        funFacts.add("Did you know the Gamesys' group revenue was almost 170 million pounds in 2012?");
        funFacts.add("Did you know Gamesys has over 40 million users?");
        funFacts.add("Did you know that across the Gamesys group in 2012, wagering was over $6.4 Billion?");
        funFacts.add("Did you know 51% of all turns are right turns? Yeah...shocking!");
        funFacts.add("Did you know MadFox rocks?");
    }
    
    public void tellAStory() {
        FacesMessageUtil.addFacesInfoMessage(this.funFacts.get(new Random().nextInt(this.funFacts.size() - 1)));
    }
    
}
