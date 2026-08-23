I can do what spotify cant 


– Are you in a Group?
  no

– If so, who else is in your group?
  n/a

– Do you have your GitHub account set up?
  yes

– Do you have a public repository for your Project?
  yes

– What is the link to your GitHub repository?
   https://github.com/missyfang/SE450

– If you are in a group, does everyone have write access to the github repo?
  n/a

– Do you have a “Hello World” program that compiles and runs?
    yes

– Where is the entry point to your project? (src/main/Main.java for
example)
    src/main/main.java


### SPRINT 2 Description 
I want to build an application that integrates with the spotify api. There is a lot of things this application could do. A feature I think has been missing from spotify is a timeline playlist generator or a "Music time machine". Where the user provide a date or date range and gets back a playlist of their most played songs during that time. I think that ill be able to accomplish at least this and maybe some other features too! 

### SPRINT 3 
## New FE and the template pattern
I added a small frontend in this iteration which is a sort of login page instead of typing directly with the console. Im using JS for the frontend and using java httpserver library and the http.exchange method to respond to request to the front end instead of spinning up a whole controller. I chose to use JS instead of a java specific package since ill be able to build a more visually appealing UI with the packages that support JS than those that support java. Once I began using this library to respond to request I saw a opportunity to use the template pattern. This is because I was calling almost identical code to serve different pages with only small differences between them. So I had HtmlPageServer implment the shared logic and then had each page implement its own version of getFileName(). The use case is small right not but will hopefully grow as pages are added to the FE.
## Using the builder pattern 
I saw building the HTTP request to a auth code and token to be an obvious use case for the builder pattern. there was an clear chaining of commands to build a single object in my original implementation. I think there are existing libraries i could have used to do this but i decided to build my own builder class. This also inspired me to build custom methods on top of the java http builder class to fit my specific use cases in other scenarios when a http request was needed.
## Struggles
Im not struggling to use design patterns, but i am struggling to justify them some times since the project is so small. 
## New Feature Ideas
I want to add a new feature "language playlist builder". I like a lot of songs in different language and wish i could filter on that language to create playlist. spotify does not let you do this but i think i can use the api to build it myself!

### SPRINT 4
## Using the command pattern as core pattern
The command patterns is one of my favs i think its so useful. I chose to use it because i saw potential for a command like "GetSongAttributes" to be used for a bunch of different features. Ill next implement a invoker to trigger the commands in a specific order to accomplish the goal of a feature.

## Using strategy pattern for test fakes
I used the strategy pattern to use a test version
of the algorithm for detecting a language from lyrics. This depends on a third party library but i don't not want my test to be dependent on that library. If the library change or broke my test would break even if the code was still good.

## Struggles
I don't know how I'm  going to get lyrics of a song without paying for them. im going to try last.fm first but i might have to switch gears by determining language by title, artist name, and market. this is way more complicated and less accurate tho.

## New Feature Ideas
My main goal is the language playlist builder. I get a mood playlist builder out of it for free as well. I think i could expand this to make the possible moods more varied and complex.


#### Final Sprint

## Using the proxy pattern for caching API calls
We added caching to reduce Spotify API quota usage. 

## Using the factory pattern to create a feature workflow
Handler methods are no longer coupled to a specific workflow implementation and just need the factory and interface.

## Struggles
The gratest struggle this sprint was the spotify quota that they introduced. I had not hit the limit in a single session before so i was unaware it existed. it is the perfect oppotunity to use the proxy pattern tho since a cache will reduce unnecessary calls. 

## Third party
- Spotify

- Lingua (`com.github.pemistahl:lingua:1.2.2`) : to detect the language

- Kotlin : needed by Lingua 

- Last.fm : used to fetch lyrics maybe...

