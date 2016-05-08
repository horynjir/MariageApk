/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr2.fel.cvut.cz.mariageapk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

/**
 *
 * @author Jiří
 */
public class Arena extends Activity {
    private Card[] card;
    private Card[] talon = new Card[2];
    private Player[] player = new Player[3];
    private Player player1, player2, player3, notBot, winner, defeated, expectant;
    private int removed, flek, game, trump, localTrump, turnCount, playerCount, tp, swap, licitationPhase = 0;
    private int turnPhase;
    private boolean playing=false;
    private ImageButton[] playerCard=new ImageButton[10];
    private ImageView[] bot1=new ImageView[10];
    private ImageView[] bot2=new ImageView[10];
    private ImageView[] playerTalon=new ImageView[2];
    private ImageButton[] trumps = new ImageButton[4];
    private ImageView[] littleDeckImage = new ImageView[3];
    private Card[] littleDeck = new Card[3];
    private SeekBar removeDeck;
    private TextView count;
    private TextView actual, coins1, coins2, playerCoins, name1, name2, playerName, playedGame, message;
    private Button yes, no, cancel, turn, result, nextRound;
    private boolean clickable=false;
    private ImageView trumpImage;
    private String name;
    private int continueGame;
    final static String ERROR = "Something is wrong";

    private static final Logger LOG = Logger.getLogger(Arena.class.getName());

    /**
     *This method is called after start this activity. It makes new players, deck and shuffle it, initialize components and starts game.
     * If button continue was clicked, this method starts loadData method which tryes to load saved data from file
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning);
        initialize();
        name = getIntent().getStringExtra("SLOVO");
        continueGame = getIntent().getIntExtra("GAME", 0);
        if(continueGame==0) {
            LOG.info("New game was created");
            player1 = new Player(name, false, 50, 1);
            player2 = new Player("Bot Bert", true, 50, 2);
            player3 = new Player("Bot Rudolf", true, 50, 3);

            addPlayer(player1);
            addPlayer(player2);
            addPlayer(player3);
            MakeDeck deck = new MakeDeck();
            deck.setDeck();
            this.card = deck.getDeck();
            deck.shuffleDeck(card);
            actualize();
            for(int i=0; i< 3; i++){
                if(!player[i].isBot()){
                    notBot=player[i];
                    break;
                }
            }
            game();
       }
        else loadData();
    }

    /**
     * This method is called after stop of this app and is saves important data to load them in future. It saves them to txt file
     * It saves name, count of coins, order of players and cards
     */
    protected void onStop(){
        LOG.info("Program is trying to save data");
        super.onStop();
        BufferedWriter writer = null;
        try {
            FileOutputStream fileOutputStream = openFileOutput("file.txt", Context.MODE_WORLD_READABLE);
            writer=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            try {
                writer.write(name);
                writer.newLine();
                writer.write(Integer.toString(swap));
                writer.newLine();
                for(int i=0; i<3; i++) {
                    writer.write(Integer.toString(player[i].getCoins()));
                    writer.newLine();
                }
                for(int i=0; i<32; i++) {
                    writer.write(Integer.toString(card[i].getId()));
                    writer.newLine();
                }
                writer.write(Integer.toString(removed));
                writer.newLine();
                writer.flush();
                Toast.makeText(Arena.this, "Game saved", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                LOG.info(String.valueOf(e));
            }
        } catch (FileNotFoundException e) {
            LOG.info(String.valueOf(e));
        }
        finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    LOG.info(String.valueOf(e));
                }
            }
        }
    }

    /**
     * this method is called if user want to continue in previous game. It loads data from txt file and set user name,
     * count of coins, cards and other
     */
    public void loadData(){
        LOG.info("Program is trying to load data");
        BufferedReader reader=null;
        try {
            FileInputStream fileInputStream = openFileInput("file.txt");
            reader=new BufferedReader(new InputStreamReader(fileInputStream));
            String temp="";
            if((temp=reader.readLine())!=null){
                name=temp;
            }

            player1 = new Player(name, false, 50, 1);
            player2 = new Player("Bot Bert", true, 50, 2);
            player3 = new Player("Bot Rudolf", true, 50, 3);
            addPlayer(player1);
            addPlayer(player2);
            addPlayer(player3);

            if((temp=reader.readLine())!=null){
                swap=Integer.parseInt(temp)-1;
                for(int i=0; i<Integer.parseInt(temp); i++){
                    swapPlayers();
                }
            }

            for(int i=0; i<3; i++){
                if((temp=reader.readLine())!=null) {
                    player[i].setCoins(Integer.parseInt(temp));
                }
            }
            MakeDeck deck = new MakeDeck();
            deck.setDeck();
            this.card = deck.getDeck();
            int[] ides = new int[32];
            Card[] tempcard = new Card[32];
            for(int i=0; i<32; i++){
                if((temp=reader.readLine())!=null) {
                    ides[i]=Integer.parseInt(temp);
                }
            }
            for(int j=0; j<32; j++){
                for(int i=0; i<32; i++){
                    if(card[i].getId()==ides[j]) tempcard[j]=card[i];
                }
            }
            card=tempcard;
            actualize();
            for(int i=0; i< 3; i++){
                if(!player[i].isBot()){
                    notBot=player[i];
                    break;
                }
            }
            if((temp=reader.readLine())!=null) {
                removed=Integer.parseInt(temp);
            }
            if(removed==0) {
                game();
            }
            else {
                deal();
            }

        } catch (FileNotFoundException e) {
            this.finish();
            LOG.info(String.valueOf(e));
        } catch (IOException e) {
            LOG.info(String.valueOf(e));
        }
        finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.info(String.valueOf(e));
                }
            }
        }
    }

    /**
     * This adds new player to an array for better work
     */
    private void addPlayer(Player player) {
        LOG.info("Player was added to game");
        this.player[playerCount] = player;
        playerCount++;
    }

    /**
     * At first, this method actualize infromation on screen. Than it makes helpful Player notBot, which helps to control decisions of user.
     * Finally it lets remove deck player on last position (if player is not a bot, seek bar is used)
     * After removing of deck, dealing of cards will start
     */
    private void game(){
        actual.setText("Removing of deck");
        if(!player[2].isBot()) {
            removeDeck.setVisibility(View.VISIBLE);
            count.setVisibility(View.VISIBLE);
            message.setVisibility(View.VISIBLE);
            message.setText("Set count of card for \nremove, please");
            removeDeck.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        int progress;

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            count.setText(i + " / " + seekBar.getMax());
                            progress = i;
                            LOG.info("SeekBar is using");
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            //nothing, because it is not needed
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            message.setVisibility(View.GONE);
                            removeDeck.setVisibility(View.GONE);
                            count.setVisibility(View.GONE);
                            card = player[2].removeDeck(card, progress);
                            removed = 1;
                            deal();
                        }
                    }
            );
        }
        else {
            card=player[2].removeDeck(card, (int) (Math.random() * 22 + 5));
            removed=1;
            deal();
        }
    }

    /**
     * These for loops deals cards to players in two rounds - 2 times 5 cards to each player and 2 to talon
     * After this licitation starts. If player is not bot, it makes yes and no buttons visible for control his decision
     */
    private void deal(){
        LOG.info("Cards are dealing");
        actual.setText("Dealing");
        int temp = 0;
        int pc=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                player[i].addCard(card[temp]);
                if(!player[i].isBot()){
                    playerCard[pc].setImageResource(card[temp].getId());
                    playerCard[pc].setVisibility(View.VISIBLE);
                    pc++;
                }
                temp++;
            }
        }
        for (int i = 0; i < 2; i++) {
            talon[i] = card[temp];
            temp++;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                player[i].addCard(card[temp]);
                if(!player[i].isBot()){
                    playerCard[pc].setImageResource(card[temp].getId());
                    playerCard[pc].setVisibility(View.VISIBLE);
                    pc++;
                }
                temp++;
            }
        }
        for(int i=0; i<10; i++){
            bot1[i].setVisibility(View.VISIBLE);
            bot2[i].setVisibility(View.VISIBLE);
        }

        licitationPhase++;
        if(!player[0].isBot() || !player[2].isBot()) {
            LOG.info("Program is waiting for user input");
            message.setVisibility(View.VISIBLE);
            message.setText("Do you want play higher game (Betl)?");
            yes.setVisibility(View.VISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        else {
            firstLicitation(game, player[0], player[2], player[1]);
        }

    }

    /**
     * This method is called after clicking on button yes or on button no and saves result (0 or 1) to helpful Player.
     * In dependation on licitation phase is than started next method
     */
    public void yesOrNo(View view){
        LOG.info("Button was clicked");
        message.setVisibility(View.GONE);
        switch (view.getId()){
            case R.id.yes:
                notBot.setYesOrNo(1);
                break;
            case R.id.no:
                notBot.setYesOrNo(0);
                break;
            default:
                LOG.warning(ERROR);
        }
        yes.setVisibility(View.GONE);
        no.setVisibility(View.GONE);
        switch (licitationPhase){
            case 1:
                firstLicitation(game, player[0], player[2], player[1]);
                break;
            case 2:
                secondLicitation(game, winner, player[1], defeated);
                break;
            case 3:
                flek(winner, defeated, expectant);
                break;
            default:
                LOG.warning(ERROR);
        }
    }

    /**
     * If player wins licitation four imagesButtons are setted visible. On click on one of them is called this method which sets color of trumps and call next method
     */
    public void trumps(View view){
        LOG.info("Some trump should be chosen");
        switch (view.getId()){
            case R.id.hearts:
                trump=0;
                break;
            case R.id.bells:
                trump=1;
                break;
            case R.id.acorns:
                trump=3;
                break;
            case R.id.leaves:
                trump=2;
                break;
            default:
                trump=0;
        }
        for(int i=0; i<4; i++){
            trumps[i].setVisibility(View.GONE);
        }
        playerFlek();
    }

    /**
     * This is one of most complex method. If user is seting talon or playing, boolean clickable is set to true, so imageButtons are clickable and we control which card is clicked.
     * Than there are two ways of code. First one is when players are playing - method playing is called. On other side setted card is swaped with card in talon.
     * Int cp means card position in array, cp=-1 means that no card is setted to swap
     * Int tp means talon phase. There are 2 cards in talon so the second way of code is called just 2 times and than are buttons set unclickable and next method is called
     */
    public void clickCard(View view){
        LOG.info("User clicked on card");
        if(clickable) {
            Card temp;
            int cp = 0; //card position
            switch (view.getId()) {
                case R.id.c1:
                    cp = 0;
                    break;
                case R.id.c2:
                    cp = 1;
                    break;
                case R.id.c3:
                    cp = 2;
                    break;
                case R.id.c4:
                    cp = 3;
                    break;
                case R.id.c5:
                    cp = 4;
                    break;
                case R.id.c6:
                    cp = 5;
                    break;
                case R.id.c7:
                    cp = 6;
                    break;
                case R.id.c8:
                    cp = 7;
                    break;
                case R.id.c9:
                    cp = 8;
                    break;
                case R.id.c10:
                    cp = 9;
                    break;
                case R.id.cancel:
                    cp = -1;
                    break;
                default:
                    cp = 1;
            }
            if(playing){
                playing(cp);
            }
            else {
                if (cp > -1) {
                    temp = winner.getPlayerCard(cp);
                    winner.setPlayerCard(cp, winner.getTalonCard(tp));
                    winner.setTalonCard(tp, temp);
                    playerTalon[tp].setImageResource(winner.getTalonCard(tp).getId());
                    playerCard[cp].setImageResource(winner.getPlayerCard(cp).getId());
                }
                tp++;
                if(tp==1) {
                    Toast.makeText(Arena.this, "Set card for swap with second card in talon, X for nothing", Toast.LENGTH_LONG).show();
                }
                if (tp > 1) {
                    clickable = false;
                    playerTalon[0].setVisibility(View.GONE);
                    playerTalon[1].setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    playerSetTrump();
                }
            }
        }
    }

    /**
     * This method is called if game is playing and int sets card which was chosen by user. At first it finds out in which position user is and it lets him set card. If turn phase is 1 (user begins turn) local trump is setted too.
     * If user choose wrong card it lets him set another. In other way it add picked card to the little deck and picture it on screen. Than in dependency on turn phase is called next method.
     * Decreasing of color of chosen card by 4 is just helpful to easier counting of trumps and local trumps. It is increased by the same value at the and of each round.
     * @param cp is card position. It is position of card in array
     */
    private void playing(int cp) {
        LOG.info("Player set some card");
        Card temp;
        Player tempPlayer = winner;
        switch (turnPhase) {
            case 1:
                tempPlayer = winner;
                break;
            case 2:
                tempPlayer = defeated;
                break;
            case 3:
                tempPlayer = expectant;
                break;
            default:
                LOG.warning(ERROR);
        }
        temp = tempPlayer.getPlayerCard(cp);
        if (turnPhase == 1) {
            localTrump = temp.getColor();
        }
        tempPlayer.trumpsCount(localTrump);
        int localTrumpCount = tempPlayer.getLocalTrumpCount();
        int trumpCount = tempPlayer.getTrumpCount();
        if (localTrumpCount == 0 && trumpCount > 0 && temp.getColor() != trump) {
            Toast.makeText(Arena.this, "Use trump", Toast.LENGTH_SHORT).show();
        } else if (localTrumpCount > 0 && temp.getColor() != localTrump) {
            Toast.makeText(Arena.this, "Choose right color", Toast.LENGTH_SHORT).show();
        } else {
            actual.setText("Turn completed");
            clickable = false;
            littleDeck[turnPhase - 1] = temp;
            littleDeckImage[turnPhase - 1].setImageResource(temp.getId());
            littleDeckImage[turnPhase - 1].setVisibility(View.VISIBLE);
            playerCard[cp].setVisibility(View.INVISIBLE);
            switch (turnPhase) {
                case 1:
                    winner = tempPlayer;
                    opponent1Turn();
                    tempPlayer.getPlayerCard(cp).setColor(tempPlayer.getPlayerCard(cp).getColor() + 4);
                    break;
                case 2:
                    defeated = tempPlayer;
                    opponent2Turn();
                    tempPlayer.getPlayerCard(cp).setColor(tempPlayer.getPlayerCard(cp).getColor() + 4);
                    break;
                case 3:
                    expectant = tempPlayer;
                    comparison();
                    tempPlayer.getPlayerCard(cp).setColor(tempPlayer.getPlayerCard(cp).getColor() + 4);
                    if (turnCount < 10) {
                        turn.setVisibility(View.VISIBLE);
                    }
                    else {
                        result.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    LOG.warning(ERROR);
            }
        }
    }

    /**
     *  This method begins licitate game, it means it sets "value" and type of game. Winner of licitation has some benefits, It has two rounds
     * @param level is type of current game
     * @param deffender is licitating Player with advantage
     * @param attacker is licitating Player  with disadvantage
     *                 If deffender and attacker want play higher game, higher game will play deffender
     * @param exp is waiting player
     */
    private void firstLicitation(int level, Player deffender, Player attacker, Player exp) {
        LOG.info("First licitation begins");
        actual.setText("First licitaion");
        game=level;
        winner = deffender;
        defeated = attacker;
        this.expectant=exp;
        int deffLic, attLic;
        if(deffender.isBot()){
            if(attacker.isBot()) {
                deffLic = deffender.licitate(game+1);
                attLic = attacker.licitate(game+1);
            }
            else{
                deffLic = deffender.licitate(game+1);
                attLic = notBot.getYesOrNo();
            }
        }
        else{
            deffLic = notBot.getYesOrNo();
            attLic = attacker.licitate(game + 1);
        }
        if(deffLic==1){
            game++;
            winner=deffender;
            defeated=attacker;
        }
        else if(attLic==1){
            game++;
            winner=attacker;
            defeated=deffender;
        }
        else{
            winner=deffender;
            defeated=attacker;
        }
        licitationPhase++;
        if(!winner.isBot() || !expectant.isBot()) {
            message.setVisibility(View.VISIBLE);
            message.setText("Do you want play higher game (Durch)?");
            yes.setVisibility(View.VISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        else {
            secondLicitation(game, winner, expectant, defeated);
        }
    }

    /**
     *  This method begins licitate game, it means it sets "value" and type of game. Winner of licitation has some benefits. This is a second round of licitation
     * @param level is type of current game
     * @param deffender is licitating Player with advantage
     * @param attacker is licitating Player  with disadvantage
     *                 If deffender and attacker want play higher game, higher game will play deffender
     * @param exp is waiting player
     */
    private void secondLicitation(int level, Player deffender, Player attacker, Player exp) {
        LOG.info("Second licitation begins");
        actual.setText("Second licitation");
        game=level;
        winner = deffender;
        defeated = attacker;
        expectant=exp;
        int deffLic, attLic;
        if(deffender.isBot()){
            if(attacker.isBot()) {
                deffLic = deffender.licitate(game+1);
                attLic = attacker.licitate(game+1);
            }
            else{
                deffLic = deffender.licitate(game+1);
                attLic = notBot.getYesOrNo();
            }
        }
        else{
            deffLic=notBot.getYesOrNo();
            attLic = attacker.licitate(game + 1);
        }

        if(deffLic==1){
            game++;
            winner=deffender;
            defeated=attacker;
        }
        else if(attLic==1){
            game++;
            winner=attacker;
            defeated=deffender;
        }
        else{
            winner=deffender;
            defeated=attacker;
        }
        setPlayedGame();
        talon(winner);
    }

    /**
     * Player with advantage gets two more card and he can swap two his cards with them
     * If Player is not bot, cards will be set as clickable
     * @param winner is Player who won licitation so he get talon
     */
    private void talon(Player winner){
        actual.setText("Setting talon");
        Player playerTemp=winner;
        if(playerTemp.isBot()){
            LOG.info("Bot gets talon");
            talon = playerTemp.setTalon(talon);
            trump = playerTemp.chooseTrumfs();
            playerFlek();
        }
        else{
            LOG.info("User gets talon");
            Toast.makeText(Arena.this, "Set card for swap with first card in talon, X for nothing", Toast.LENGTH_LONG).show();
            talon = playerTemp.setTalon(talon);
            cancel.setVisibility(View.VISIBLE);
            clickable=true;
            for(int i=0; i<2; i++){
                playerTalon[i].setImageResource(playerTemp.getTalonCard(i).getId());
                playerTalon[i].setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * After swap of cards with talon, trumps will be visible for setting them on screen, if winner of licitation is not a bot.
     */
    private void playerSetTrump(){
        for(int i=0; i<4; i++){
            trumps[i].setVisibility(View.VISIBLE);
        }
    }

    /**
     * This sets value of game by player. It makes visible accept or decline button on screen
     * Of nobody wants flek (his cards are not good) round ends
     */
    private void playerFlek(){
        LOG.info("Program will be expecting user action for flecking");
        actual.setText("Fleking");
        setTrumpIcon();
        licitationPhase++;
        message.setVisibility(View.VISIBLE);
        message.setText("Do you want flek play this game?");
        yes.setVisibility(View.VISIBLE);
        no.setVisibility(View.VISIBLE);
    }

    /**
     * this is just informative method for user. It sets text on screen, which says which type of game is currently playing
     */
    private void setPlayedGame(){
                switch(game){
                    case 0:
                        playedGame.setText("Game");
                        break;
                    case 1:
                        playedGame.setText("Betl");
                        break;
                    case 2:
                        playedGame.setText("Durch");
                        break;
                    default:
                        LOG.warning(ERROR);
                }
                playedGame.setVisibility(View.VISIBLE);
    }

    /**
     * This is only informative method for user. It set icon on display which represents actual trumps
     */
    private void setTrumpIcon(){
        switch(trump){
            case 0:
                trumpImage.setImageResource(R.mipmap.hearts);
                break;
            case 1:
                trumpImage.setImageResource(R.mipmap.bells);
                break;
            case 2:
                trumpImage.setImageResource(R.mipmap.leaves);
                break;
            case 3:
                trumpImage.setImageResource(R.mipmap.acorns);
                break;
            default:
                LOG.warning(ERROR);
        }
        trumpImage.setVisibility(View.VISIBLE);
    }

    /**
     * At first this method find out who want to flek and plays game with current cards. After it on depend on rules it set value of flek (max 2)
     * @param player is winner of licitation
     * @param opponent1 plays agains player
     * @param opponent2 plays against player
     */
    private void flek(Player player, Player opponent1, Player opponent2) {
        LOG.info("Now is flecking phase");
        Player[] flekPlayer = new Player[3];
        int[] flekBool = new int[3];
        flekPlayer[0]=player;
        flekPlayer[1]=opponent1;
        flekPlayer[2]=opponent2;

        for(int i=0; i<3; i++){
            if(flekPlayer[i].isBot){
                flekBool[i]=flekPlayer[i].flek();
            }
            else {
                flekBool[i]=flekPlayer[i].getYesOrNo();
            }
        }
        if (flekBool[1] == 1) {
            flek++;
            if (flekBool[0] == 1) {
                flek++;
            }
        } else if (flekBool[2] == 1) {
            flek++;
            if (flekBool[0] == 1) {
                flek++;
            }
        }
        choosingGame();
    }

    /**
     * This method give user information which game is played and it sets color of trumps to each player
     * If flek is 0, no game is playing and after clicking on button result round ends
     */
    private void choosingGame() {
        String str="";
        if (flek != 0) {
            LOG.info("Players are able to play");
            switch (game){
                case 0:
                    str+="Game";
                    break;
                case  1:
                    str+="Betl";
                    break;
                default:
                    str+="Durch";
            }
            Toast.makeText(Arena.this, winner.getName() + " is playing " + str, Toast.LENGTH_LONG).show();
            winner.setTrumps(trump);
            defeated.setTrumps(trump);
            expectant.setTrumps(trump);
            playing=true;
            turn.setVisibility(View.VISIBLE);
        } else {
            LOG.info("Game will not start");
            result.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Each turn has to be confirmed by user by clicking on Next turn button. Then this method is called. Player who begins game sets first card.
     * Then this card is pictured on screen. If player is not bot, cards on screen is clickable and real player set card. These methods are called ten times, becaouse each round has 10 turns
     */
    public void playerTurn(View view){
        LOG.info("First player is on turn");
        actual.setText("Your turn");
        turnCount++;
        for(int i=0; i<3; i++){
            littleDeckImage[i].setVisibility(View.GONE);
        }

        turn.setVisibility(View.GONE);
        turnPhase=1;
        if(winner.isBot){
            Card temp=winner.setCard();
            localTrump=temp.getColor();
            littleDeck[0] = temp;
            littleDeckImage[0].setImageResource(temp.getId());
            littleDeckImage[0].setVisibility(View.VISIBLE);
            bot1[turnCount-1].setVisibility(View.INVISIBLE);
            opponent1Turn();
        }
        else {
            clickable=true;
        }
    }

    /**
     * Similar to previous method. This one do not sets local trumps
     */
    private void opponent1Turn(){
        LOG.info("Second player is on turn");
        turnPhase=2;
        if(defeated.isBot){
            Card temp=defeated.setCard(localTrump);
            littleDeck[1]=temp;
            littleDeckImage[1].setImageResource(temp.getId());
            littleDeckImage[1].setVisibility(View.VISIBLE);
            bot2[turnCount-1].setVisibility(View.INVISIBLE);
            opponent2Turn();
        }
        else {
            clickable=true;
        }
    }

    /**
     * Similar to previous method. This one do not sets local trumps
     */
    private void opponent2Turn(){
        LOG.info("Third player is on turn");
        turnPhase=3;
        if(expectant.isBot){
            Card temp=expectant.setCard(localTrump);
            littleDeck[2] = temp;
            littleDeckImage[2].setImageResource(temp.getId());
            littleDeckImage[2].setVisibility(View.VISIBLE);
            if(bot1[turnCount-1].getVisibility()==View.VISIBLE){
                bot1[turnCount-1].setVisibility(View.INVISIBLE);
            }
            else{
                bot2[turnCount-1].setVisibility(View.INVISIBLE);
            }
            comparison();
            if(turnCount<10) {
                turn.setVisibility(View.VISIBLE);
            }
            else result.setVisibility(View.VISIBLE);

        }
        else {
            clickable=true;
        }
    }

    /**
     * This mehod compare points of player after each turn. Player with highest yount of points gets little deck
     */
    private void comparison() {
        LOG.info("Now cards in little deck are compared");
        int playerPoints = littleDeck[0].cardPoints(localTrump, trump, game);
        int opponent1Points = littleDeck[1].cardPoints(localTrump, trump, game);
        int opponent2Points = littleDeck[2].cardPoints(localTrump, trump, game);
        Player turnWinner;
        if (playerPoints > opponent1Points && playerPoints > opponent2Points) {
            turnWinner = winner;
        } else if (opponent1Points > playerPoints && opponent1Points > opponent2Points) {
            turnWinner = defeated;
        } else {
            turnWinner = expectant;
        }
        for (int i = 0; i < 3; i++) {
            turnWinner.addToDeck(littleDeck[i]);
        }
    }

    /**
     *This method is called after clicking on result button and it evaulate current round.
     * Who wins round depends on type of game. If Game was played, it depends on points. If Betl was played main Player wins only if he did not win every turn and in Durch he had to win avery turn to win whole round.
     * At the and it actualize infromations on screen
     */
    public void result(View view){
        LOG.info("On screen shoud be results of round");
        actual.setText("End of this round");
        String mess1=winner.getName() + " wins and he \ngets ";
        String mess2=winner.getName() + " lose and he \ngive each opponent ";
        int coins=0;
        String mess="";
        result.setVisibility(View.GONE);
        for(int i = 0; i<3;i++)
            {
                littleDeckImage[i].setVisibility(View.GONE);
            }
        if(flek==0){
            coins= winnerWins();
            message.setVisibility(View.VISIBLE);
            message.setText("Because nobody flecked, " + winner.getName() + " \ngets a standart price: " + coins + "coins");
        }
        else {
            if(game==0){
                int winnerPoints=winner.scoreOfGame();
                int opponentsPoints=defeated.scoreOfGame()+expectant.scoreOfGame();
                if(winnerPoints<opponentsPoints){
                    coins=winnerLose();
                    mess=mess2;
                }
                else{
                    coins= winnerWins();
                    mess=mess1;
                }
            }
            else if(game==1){
                if(winner.getDeckSize()!=0){
                    coins=winnerLose();
                    mess=mess2;
                }
                else{
                    coins= winnerWins();
                    mess=mess1;
                }
            }
            else if(game==2){
                if(winner.getDeckSize()!=30){
                    coins=winnerLose();
                    mess=mess2;
                }
                else {
                    coins= winnerWins();
                    mess=mess1;
                }
            }
            mess+=coins + " coins";
            message.setText(mess);
        }
        message.setVisibility(View.VISIBLE);
        actualize();
        nextRound.setVisibility(View.VISIBLE);
    }

    private int winnerWins(){
        return defeated.pay(winner, flek, game)+expectant.pay(winner, flek, game);
    }

    private int winnerLose(){
        return winner.pay(defeated, flek, game)+winner.pay(expectant, flek, game);
    }

    /**
     * This method is called after clicking on button Next round and it will restart important variables and starts new round.
     * It also swap players, because each of them has to play in every position.
     */
    public void nextRound(View view){
        LOG.info("Next round should starts");
        lineUpCards();
        message.setVisibility(View.GONE);
        nextRound.setVisibility(View.GONE);
        for(int i=0; i<3; i++){
            player[i].restart(flek);
        }
        restart();
        swapPlayers();
        game();
    }

    /**
     * Because deck is shuffled just one times its important to let right order of cards. Method lineUpCards do this - it takes cards from players and lines them up
     */
    private void lineUpCards(){
        LOG.info("Cards should be lined up");
        int k=0;
        Card[] deckTemp;
        for(int i=0; i<3; i++){
            deckTemp=player[i].getDeck();
            for(int j=0; j<player[i].getDeckSize(); j++){
                card[k]=deckTemp[j];
                k++;
            }
        }
        for (int i=0; i<2; i++){
            card[k]=talon[i];
            k++;
        }
    }

    /**
     * This method is called after every round and it set important variables to default value
     */
    private void restart(){
        LOG.info("Variables are set to default");
        removed=0;
        flek=0;
        game=0;
        trump=0;
        localTrump=0;
        turnPhase=0;
        tp=0;
        licitationPhase=0;
        playing=false;
        turnCount=0;
        trumpImage.setVisibility(View.GONE);
        playedGame.setVisibility(View.GONE);
    }

    /**
     * This method is called after some informative change - it actualize info on screen
     */
    private void actualize() {
        LOG.info("Infromations are actualized");
        String str = " coins";
        for(int i = 0;i<3;i++){
            if (player[i].getId() == 1) {
                playerName.setText(player[i].getName());
                playerCoins.setText(player[i].getCoins() + str);
            } else if (player[i].getId() == 2) {
                name1.setText(player[i].getName());
                coins1.setText(player[i].getCoins() + str);
            } else {
                name2.setText(player[i].getName());
                coins2.setText(player[i].getCoins() + str);
            }
        }
    }

    private void swapPlayers() {
        LOG.info("Players were swapped");
        Player temp = player[2];
        player[2] = player[1];
        player[1] = player[0];
        player[0] = temp;
        for (int i = 0; i < 3; i++) {
            player[i].setCardCount(0);
        }
        swap++;
    }

    /**
     * This method "fins view by id" for every GIU component in Arena class
     */
    private void initialize(){
        bot1[0]=(ImageView)findViewById(R.id.bot101);
        bot1[1]=(ImageView)findViewById(R.id.bot102);
        bot1[2]=(ImageView)findViewById(R.id.bot103);
        bot1[3]=(ImageView)findViewById(R.id.bot104);
        bot1[4]=(ImageView)findViewById(R.id.bot105);
        bot1[5]=(ImageView)findViewById(R.id.bot106);
        bot1[6]=(ImageView)findViewById(R.id.bot107);
        bot1[7]=(ImageView)findViewById(R.id.bot108);
        bot1[8]=(ImageView)findViewById(R.id.bot109);
        bot1[9]=(ImageView)findViewById(R.id.bot110);

        bot2[0]=(ImageView)findViewById(R.id.bot201);
        bot2[1]=(ImageView)findViewById(R.id.bot202);
        bot2[2]=(ImageView)findViewById(R.id.bot203);
        bot2[3]=(ImageView)findViewById(R.id.bot204);
        bot2[4]=(ImageView)findViewById(R.id.bot205);
        bot2[5]=(ImageView)findViewById(R.id.bot206);
        bot2[6]=(ImageView)findViewById(R.id.bot207);
        bot2[7]=(ImageView)findViewById(R.id.bot208);
        bot2[8]=(ImageView)findViewById(R.id.bot209);
        bot2[9]=(ImageView)findViewById(R.id.bot210);

        playerCard[0]=(ImageButton)findViewById(R.id.c1);
        playerCard[1]=(ImageButton)findViewById(R.id.c2);
        playerCard[2]=(ImageButton)findViewById(R.id.c3);
        playerCard[3]=(ImageButton)findViewById(R.id.c4);
        playerCard[4]=(ImageButton)findViewById(R.id.c5);
        playerCard[5]=(ImageButton)findViewById(R.id.c6);
        playerCard[6]=(ImageButton)findViewById(R.id.c7);
        playerCard[7]=(ImageButton)findViewById(R.id.c8);
        playerCard[8]=(ImageButton)findViewById(R.id.c9);
        playerCard[9]=(ImageButton)findViewById(R.id.c10);

        playerTalon[0]=(ImageView)findViewById(R.id.talon1);
        playerTalon[1]=(ImageView)findViewById(R.id.talon2);
        trumps[0]=(ImageButton)findViewById(R.id.hearts);
        trumps[1]=(ImageButton)findViewById(R.id.bells);
        trumps[2]=(ImageButton)findViewById(R.id.acorns);
        trumps[3]=(ImageButton)findViewById(R.id.leaves);
        littleDeckImage[0]=(ImageView)findViewById(R.id.deck1);
        littleDeckImage[1]=(ImageView)findViewById(R.id.deck2);
        littleDeckImage[2]=(ImageView)findViewById(R.id.deck3);
        removeDeck = (SeekBar)findViewById(R.id.remove);
        count=(TextView)findViewById(R.id.count);
        actual=(TextView)findViewById(R.id.actual);
        yes = (Button)findViewById(R.id.yes);
        no = (Button)findViewById(R.id.no);
        cancel = (Button)findViewById(R.id.cancel);
        turn = (Button)findViewById(R.id.turn);
        result=(Button)findViewById(R.id.result);
        name1=(TextView)findViewById(R.id.name1);
        name2=(TextView)findViewById(R.id.name2);
        playerName=(TextView)findViewById(R.id.playerName);
        coins1=(TextView)findViewById(R.id.coins1);
        coins2=(TextView)findViewById(R.id.coins2);
        playerCoins=(TextView)findViewById(R.id.playerCoins);
        trumpImage=(ImageView)findViewById(R.id.trump);
        playedGame=(TextView)findViewById(R.id.game);
        nextRound=(Button)findViewById(R.id.nextRound);
        message=(TextView)findViewById(R.id.message);
    }

}
