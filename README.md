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
UML diagrams
- https://www.figma.com/board/vxkZZkvwJ7LMByFql9GWFe/Builder-pattern-uml?node-id=0-1&t=bHezfbGcRtKGMkHk-1

