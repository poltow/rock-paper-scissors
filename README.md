# Rock Paper Scissors CODE CHALLENGE 

Requirements: 

    First part:
        - Write a program to play rounds of Rock, Paper, Scissors (https://en.wikipedia.org/wiki/Rock-paper-scissors).
        - There will be 2 kinds of players, one should always choose randomly, 
        the other should always choose rock.
        - The program will be a web, using a java Rest API and a frontend view using javascript
        - “Play Round” button that will play an automatic round
        - Field showing the number of rounds played by that user so far.
        - Table showing the rounds played, with 3 columns: what 1st player chose, 
        what second chose, and the result  of the round (that could be player 1 wins, player 2 wins or draw)
        - There will be a button: “Restart game” that will set round count for that user to 0 and also clean the table.
        - Many users accessing the url in their own browsers should be able to play the game independently, 
        with their own counters and rounds info tables.
 
    Second part:
        - Add a second simple view with the next information:
            - total rounds played
            - total Wins for first players
            - total Wins for second players
            - total draws
        - These totals should consider all the rounds of all the games played by all users. 
        (even if we clicked in “Restart button”, these games should be considered as well)
        - Don’t use database, just keep the information you need in memory.
 
    Technical requirements:
        - JVM 7 or higher
        - Use a rest api built in Java, and u should use javascript for the front end (and any framework/library u like, as jquery, react..).
        - The code should have high test coverage.
        - The use of maven, git, java8, etc will be evaluable.
        - Keep it simple - remember: we’re not looking for the perfect “correct” solution, but a basis for discussion and development.
        - Approach: ideally “test-driven”


# SOLUTION:

    Development tools/frameworks
        - Eclipse
        - Maven
        - Spring Boot
        - JUnit, Mockito, PowerMock


## Running the app
    - By command line: mvn spring-boot:run
    - The app will run on http://localhost:8080


## Use of the program
    The program can be accesed by API Rest requests and by Web App.

# API REST DOCS

### Create game

    POST /api/v1/game

    This will create a new game. This is a necessary first step to start playing.

    Request body
        .name: Game user name.
        .strategy: The strategy that the user want to play. Possibles values are PLAY_ROCK or PLAY_RANDOM.

    Request Example
        curl -v -X POST http://localhost:8080/api/v1/game \
        -H "Content-Type: application/json" \
        -d
            '{
            "name":"JOHN",
            "strategy":"PLAY_RANDOM"
            }'


### Play Round

    POST /api/v1/round

    This will play a new round based on the selected strategy for the created game.

    Request body
        .name: Game user name.

    Request Example
        curl -v -X POST http://localhost:8080/api/v1/round \
        -H "Content-Type: application/json" \
        -d
            '{
            "name":"JOHN"
            }'


### Get Rounds 

    GET /api/v1/rounds/{name}

    This will return all the user played rounds for the current game.

    Query parameters 
        .name: Game user name.

    Request Example
        curl -v -X GET http://localhost:8080/api/v1/rounds/JOHN \
        -H "Content-Type: application/json" 

    Response example
        [
            {
                "playerShape": "PAPER",
                "opponentShape": "ROCK",
                "result": "WIN"
            },
            {
                "playerShape": "ROCK",
                "opponentShape": "ROCK",
                "result": "DRAW"
            },
            {
                "playerShape": "ROCK",
                "opponentShape": "ROCK",
                "result": "DRAW"
            }
        ]


### Restart game

    DELETE /api/v1/rounds/{name}

    This will reinitialize current game.

    Query parameters 
        .name: Game user name.

    Request Example
        curl -v -X DELETE http://localhost:8080/api/v1/rounds/JOHN \
        -H "Content-Type: application/json" 


### End game

    DELETE /api/v1/game/{name}

    This will delete/end current game. Use this if you want to change user 
    strategy, by creating a new game.

    Query parameters 
        .name: Game user name.

    Example
        curl -v -X DELETE http://localhost:8080/api/v1/game/JOHN \
        -H "Content-Type: application/json" 


### Get History 

    GET /api/v1/history

    This will return the summary for all the rounds in all the games that 
    each user ever played (since app is running).

    Request Example
        curl -v -X GET http://localhost:8080/api/v1/history \
        -H "Content-Type: application/json" 

    Response example
        [
            {
                "userName": "MARY",
                "wins": 0,
                "loses": 0,
                "draws": 2,
                "total": 2
            },
            {
                "userName": "JOHN",
                "wins": 0,
                "loses": 2,
                "draws": 1,
                "total": 3
            }
        ]


# WEB APP

    Open browser and go to http://localhost:8080

    Init page:
        -Enter the user name and select the user strategy.
        -Press button to start playing

    Play page:
        - Press "Play Round" button to play a new round.
        - Press "Restart Game" button to restart current game. 
        This will set round count to 0 and also will clean the rounds info table.
        - Press "Show Users History" button to display totals for all the rounds in all 
        the games that each user ever played (since app is running).
        - Press "End game/Change user" button to finish the game and return to init page.
        
    Known bugs: It is possible to play the same user game in two separate browsers, 
    but the strategy of the game cannot be changed at second user logging time. 
    If the 1st user choosed ROCK and the 2nd one choosed RANDOM, the game strategy 
    will be ROCK and the 2nd user will see RANDOM as displayed strategy.






