package com.example.reactiveapp.service;

import com.example.reactiveapp.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by mario on 27.6.2017..
 */

public class NewsService {
    private NewsService() {
    }

    private static NewsService instance;

    public static NewsService getInstance() {
        if (instance == null) {
            instance = new NewsService();
        }
        return instance;
    }

    public static int LOADED_NEWS_ITEMS = 0;

    private NewsModel activeNews;

    public NewsModel getActiveNews() {
        return activeNews;
    }

    public void setActiveNews(NewsModel activeNews) {
        this.activeNews = activeNews;
    }

    public Flowable<NewsModel> getNewsItems() {

        final List<NewsModel> list = new ArrayList<>();

        list.add(new NewsModel("RHEANNA O'NEIL BELLOMO",
                "You Need To Try Krispy Kreme's Fanciest Donuts Of All Time",
                "Just when we thought we'd found the most over-the-top donuts Krispy Kreme had to offer — remember those ultra premium crème brûlée and cafe latte bunny donuts in Japan?! — the bakery chain has completely outdone itself.\n\nA new line of extra fancy, exclusive donuts have arrived at a single Krispy Kreme location, featuring high-end ingredients like Marc de Champagne, Belgian white chocolate, and honeycomb toffee, Secret London reports.\n\nThe first is a red berry drizzle yeast donut filled raspberry jam, topped with cream cheese frosting, finished with biscuit pieces and fresh strawberries — plus a drizzle of red berry coulis and lacy crepe crumbs.\n\nThere's also a popping champagne donut filled with Marc de Champagne brandy-infused white chocolate, topped with champagne cream and Belgian chocolate shavings. Then there's popping candies and gold sugar for the final bubbly effect.\n\nThe magic unicorn donut (sigh) is another yeast donut full of multi-colored frosting that then gets dunked in purple icing before getting piped with a swirl of purple, white, and blue cream and a heavy-handed dusting of edible glitter.\n\nKrispy Kreme is going hard on the salted caramel trend with its salted caramel and honeycomb donut, which includes a salted caramel filling, a caramel glaze on top, plus salted caramel shards and a piece of honey comb on top. But it doesn't stop there: they also drizzles it with white chocolate.\n\nThe namesake Selfridges donut is full of chocolate frosting and covered in dark chocolate ganache before getting finished with gold dust. There are also Belgian chocolate stars and a candy Selfdriges \"plaque\" on top.\n\nThe luxe donuts can only be found at one location in London, inside the food hall at Selfridges, which is a high-end department store similar to Neiman Marcus or Bloomingdales. We're not saying we'd book a flight just for these bad boys, but we're also not saying we wouldn't.",
                "5 hours ago",
                "http://del.h-cdn.co/assets/17/26/640x320/landscape-1498514427-krispy-kreme-selfridges-exclusive-donut-line.jpg"));

        list.add(new NewsModel("RAYNE ELLIS",
                "Kiwi Berries Are Going To Be Your New Fruit Obsession",
                "Last month the internet went wild for miniature avocados. The pint-size fruit exploded on social media not just for its cuteness (you can fit five in your palm!) but its convenience (single servings = less waste). Now, kiwis are getting the same tiny treatment, with the kiwi berry, a grape-sized, fuzz-less kiwi you can eat on the go.\n\nThough kiwi berries are basically just mini kiwis, the flavor isn't the same. Those who've tried it say it's complex, sweet, and acidic.\n\nNative to northern regions in Asia with cooler climates — like central China, Siberia, and Japan — the kiwi berry, or Actinidia Arguta, has actually been growing in the United States for more than 150 years. So how has it remained under the radar since the 1870s? No one has ever tried to commercialize it. But now that's about to change.\n\nCollege research facilities around the country and private-label companies like Nergi are choosing to capitalize on America's newfound obsession with all things natural, trying to integrate the kiwi berry into our everyday lives.\n\nGrown in areas of the United States with cooler climates like New England, kiwi berries are the kind of fruit that can withstand very cold temperatures. Besides being really cute, the fruit is also packed with antioxidants, fiber, vitamin C, and vitamin E.\n\nSadly, these little guys aren't readily available for everyone yet. We expect kiwi berries to become more of a widespread phenomenon in the coming months, but until then all we can do is wait. And maybe keep ourselves occupied with all the other frankenfruits, like cotton candy grapes, miniature avos, and pink pineapples.",
                "13 hours ago",
                "http://del.h-cdn.co/assets/17/26/980x490/landscape-1498498316-introducing-kiwi-berries.jpg"));

        list.add(new NewsModel("RHEANNA O'NEIL BELLOMO",
                "Fans Are Calling Taco Bell's \"Double\" Chalupa A Ripoff",
                "When Taco Bell announced the debut of its new oversize Double Chalupa with double the beefy fillings, the chain generated an insane amount of hype among fans.\\n\\nIn all honesty, the double-stuffed fried tortilla looks pretty awesome, if not a little over-the-top. The trouble is, people are not getting what they've been promised — that is to say, the Tex-Mex restaurant apparently isn't delivering twice as much meat and toppings, as it's advertising. While the brand touts the Double Chalupa as clocking in at 9 ounces and photos depict it as bursting at the seams, the reality for some customers is much less exciting.\\n\\nFans have taken to — where else? — Twitter to express their discontent with the new menu item.\\n\\nTaco Bell has responded to most of these complaints on the social media platform, saying things like \"let's remake that\" or \"that's not OK, let's make up\" and requesting the user send them a direct message.\\n\\nAs Business Insider fairly notes, a giant-size shell makes it harder for customers to notice more meat, even if employees are actually filling it up with twice as much beef or chicken.\\n\\nThe company explained to the site that \"our Customer Care team has been in contact with our fans to ensure they have the best Double Chalupa experience possible, and we are connecting with our restaurant teams to ensure our customers receive Double Chalupas that exceed their expectations.\"",
                "yesterday",
                "http://del.h-cdn.co/assets/17/26/1498508413-chalupaadshot.png"));

        list.add(new NewsModel("CANDACE BRAUN DAVISON",
                "The Cake Boss Shuts Down His Bakeries Worldwide In Memory Of His Mother",
                "One week ago, Buddy Valastro got the call no child wants to hear. He had to come home, quickly — his mother's health had taken a turn, and there wasn't much time left. The Cake Boss star dropped everything to head back to New Jersey to be with his mom, Mary Valastro, in her final moments.\\n\\nAfter eight years battling the neurodegenerative disease ALS, her fight was over. Buddy shared the news with fans on Instagram last Thursday: \"She left for heaven this morning, surrounded by the family,\" he wrote. \"She is no longer suffering, and I hope she's dancing to 'I Will Survive' with my dad right now.\"\\n\\nWhile Mary retired from filming episodes of TLC's Cake Boss in 2012, she has been a central part of the Valastro family business. To honor her memory and the impact she had on everyone around her, Valastro announced that he's closing all Carlo's Bakery stores worldwide on June 27. The shops, which include several across the U.S. and one in Brazil, will be back up and running with their normal hours on Wednesday, June 28.\\n\\nBuddy took over the family business in 1994, after his father passed away. In the years that followed, his mother remained a constant source of support. Mary's battle with ALS inspired him to create the Momma Mary Foundation, in hopes of finding a cure for Lou Gehrig's disease.\\n\\n\"I've seen the most independent, lively person that I've ever met in my life — I mean, she was just like a ball of energy — reduced to not being able to do anything for herself,\" he told People in a 2014 interview. \"And I think that's the part that kills her the most, because she was the matriarch. She did so many things for everyone.\"\\n\\nFans from all over the world have flooded Buddy's and the Carlo's Bakery Instagram accounts with comments expressing their condolences and sharing their memories of Mary. \"She was a great woman, a fighting mother, loving and incomparable, charismatic like none, but always with a smile that contagious all,\" wrote one fan, NahoJ25. \"My condolence to the Valastro family ... from Venezuela accompanying them in their pain.\"\\n\\nIn a post last night, Buddy shared a recent photo of his mother, surrounded by the Cake Boss's family. \"The light of our lives,\" he captioned it.\\n\\nOthers have shared clips from the show, sharing their favorite moments of Momma Mary. \"We'll always remember her like this, HAPPY!\" wrote the Carlo's Bakery team in Texas.\\n\\nAs the family takes this day to celebrate Mary's life, Buddy has asked everyone to be patient and respectful during this difficult time.",
                "yesterday",
                "http://del.h-cdn.co/assets/17/26/640x320/landscape-1498575913-delish-buddy-cake-boss-mom.jpg"));

        list.add(new NewsModel("RAYNE ELLIS",
                "French Yogurt Is The New Greek Yogurt",
                "America has the weirdest on-again off again relationship when it comes to yogurt. Whether it's Go-gurt, Greek yogurt, or fro-yo, the country's opinion fluctuates more rapidly than the weather. And so, to revive declining sales in the category, dairy brands have been rejiggering their lineups and introducing new concepts, introducing everything from whipped mousse Greek yogurt to 100-calorie parfait cups.\\n\\nBut now we're about to welcome a totally new kind of yogurt to the American market. Part of an endeavor to create the hottest new thing in the refrigerated aisle, Yoplait has created a totally new \"French-style\" yogurt called Oui (that means \"yes\" in French). It's considered a nod to the company's French roots, though the exact definition of French-style yogurt remains to be determined.\\n\\nThis new variety is inspired by one of Yoplait's \"heritage\" recipes called \"Saveur d'Autrefois,\" which roughly translates to \"taste of yesteryear.\" It calls for only whole milk, yogurt cultures, and the real fruit included at the bottom. But what most separates this new style of yogurt from what we're used to seeing Stateside is how it's made: Each portion is cultured in the glass container it's sold in, which gives it a unique texture and voids any need for additional additives.\\n\\nThe brand touts its French yogurt as firmer than Greek yogurt, ultra thick, subtly sweet, and very fresh tasting; meanwhile, Buzzfeed described the texture as reminiscent of butter.\\n\\nAt $1.49 a pop, this spin on yogurt is a little more expensive than its traditional counterparts, but at least you get to keep (and reuse) those cute little glass containers. Yoplait plans to release eight flavors — including plain, vanilla, lemon, strawberry, blueberry, peach, coconut, and black cherry — each of which boasts less than eight ingredients and zero artificial flavors. Find it on store shelves in early July.",
                "2 days ago",
                "http://del.h-cdn.co/assets/17/26/980x490/landscape-1498575408-french-yogurt.jpg"));

        list.add(new NewsModel("RHEANNA O'NEIL BELLOMO",
                "Eating At The Exact Same Time Every Day Can Help You Lose Weight",
                "If you aren't one for routine, you may want to rethink your day-to-day approach — especially if you're prone to eating late dinners after a busy work day or skipping breakfast when you're running late. According to a new study, an erratic eating schedule could be totally messing with your health. In fact, irregular meals can lead to obesity, high blood pressure, and type 2 diabetes.\\n\\nTwo papers published in the Proceedings of the Nutrition Society found that adults who consume meals at the same time every day were less obese and had better cholesterol and insulin levels, even though they consumed more calories over all. Essentially, when you eat is just as important as what you eat. This research comes from a new category of health and nutrition called chrononutrition, which focuses on the link between your metabolism and circadian rhythms, Health.com reports.\\n\\n\"Eating inconsistently may affect our internal body clock,\" study author Gerda Pot, PhD (who's also a visiting lecturer in the Diabetes and Nutritional Sciences Division at King's College London) told the health publication. That's because your metabolic process — including appetite, digestion, and the rate your body processes fat, sugar, and cholesterol — follows a pattern that repeats every 24 hours. Once you disrupt that process by eating at random times every day, you're at risk for weight gain and other health risks.\\n\\nIf you want to implement a timely tactic, try setting a reminder or alarm on our phone, meal-planning and prepping on the weekends, making the next day's breakfast and lunch the night before, and setting up your coffee maker ahead of time.\\n\\nCombine this with mindful eating — when you cut out distractions and completely savor your food — could double your success rate of dropping a few pounds, maintaining your weight, or just feeling healthier overall.",
                "3 days ago",
                "http://del.h-cdn.co/assets/cm/15/18/320x160/5540f2e7abd3f-sa6889-2.jpg"));

        list.add(new NewsModel("RAYNE ELLIS",
                "Soft Serve In A Watermelon Slice Is Peak Summer",
                "From the man who brought us Cronuts, or croissant doughnuts, came what may be the most Instagram-able dessert ever: cookie shots. But if you think they're incredible, just wait until you see Dominique Ansel's latest creation. It's called What-A-Melon Soft Serve, and before we tell you more, you need to see it up close.\\n\\nIt's homemade watermelon soft serve, sprinkled with a dash of sea salt, and served in a hollowed-out watermelon slice with some chocolate seeds on the side.\\n\\nAs a part of Dominique Ansel Japan's second anniversary celebration, the bakery known for its creative designs and delicious flavor combinations — remember those slices of cake served inside balloons?! — has broken Instagram with another whimsical confection.\\n\\nThe downside is that it's not available at his U.S. shop, and no plans have been announced to bring the treat stateside. However, Dominique Ansel hasn't even started selling the treats yet. The first What-A-Melons will be sold on June 24, so there's still time to book a flight and plan your entire summer vacation around a single dessert. (We're not the only ones who do that, right?)\\n\\nThat isn't the only fruit-inspired frozen dessert on Ansel's menu, though. Just this week Ansel released Kiwi Sorbet Bars, and it's already popping up all over Instagram.\\n\\nMade to look like the inside of a kiwi, the treat features Tahitian ice cream, surrounded by poppy seeds and kiwi sorbet, all coated in a \"fuzzy\" milk chocolate. This treat is available in the U.S. — just head to Dominique Ansel Bakery in New York to try it yourself.",
                "4 days ago",
                "http://del.h-cdn.co/assets/17/25/980x490/landscape-1498246391-watermelone-soft-serve.jpg"));

        list.add(new NewsModel("RAYNE ELLIS",
                "This Kiddie Pool Hack Is The Only Way To Serve Drinks This Summer",
                "Considering Americans spend an estimated $6.8 billion on the Fourth of July each year, finding creative ways to wow your guests without breaking the bank is crucial (hey, all those beers, burgers and hot dogs add up fast).\\n\\nForget swan floats or epic, quasi-illegal fireworks displays — your party needs a three-tiered water fountain of alcohol. It sounds like something you'd find in the lobby of a hotel worth thousands of dollars. But this fountain isn't made of crystal – it's made up of old kiddie pools.\\n\\nBecause who needs elegance when your fingers are covered in barbecue sauce, anyway?\\n\\nThere are many ways to go about this, but the first step is making sure you have the three kiddie pools (ideally of varying sizes). That's where the ice and drinks go. Then make sure you've got two plastic bins, to sit the kiddie pools on top of, creating that majestic, towering effect.\\n\\nGrab a drill, non-rusting screws, and a wrench, and you're ready to go all Tim The Tool Man Taylor on this project. To get the fountain effect, place the smallest kiddie pool on top of one plastic bin. Once it's aligned where you want it, drill through both layers of plastic and add screws to secure the pool to the tier. Repeat with the other bin and the medium-sized pool. (For a complete, step-by-step tutorial, check out The Homestead Survival's guide.)\\n\\nOnce everything's assembled, fill the pools with ice and drinks, and now you have a fancy-ish fountain filled with booze. Just make sure to hide these instructions from your jealous family members, who didn't realize that you were so chic before drinking the night away.",
                "5 days ago",
                "http://del.h-cdn.co/assets/cm/15/10/160x80/54f94844561da_-_soda-cans.jpg"));

        return Flowable.fromIterable(list);
    }
}
