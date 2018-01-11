import extensions.*;

class Jeu_de_Loie extends Program{

    Joueur[] joueur = new Joueur[2];
    Case[] plateau = new Case[64];

    void algorithm_(){
        println(toString(loadCSV("..//res//question.csv")));
    }

    void algorithm(){
        initJoueurs();
        initCase();
        boolean tour = false;
        boolean victoire = false;

        do{
            int des;

            if (joueur[tourJoueur(tour)].nbAttente == 0) {

		String colorP = "";
		String special = "";
                int nbReponse  = 0;
                int nbQuestion = 3;
		
                /*do{

                }while(nbQuestion != 0 ||nbReponse == 2);*/

                joueur[tourJoueur(tour)].positionAvant = joueur[tourJoueur(tour)].position;
                des = lancerDes();
                delay(100);
                des = des + lancerDes();
                joueur[tourJoueur(tour)].position = position(des, tour);

		readString();
		
		clearScreen();
		cursor(1, 1);

		special += plateau[joueur[tourJoueur(tour)].position].color;
		
                switch (plateau[joueur[tourJoueur(tour)].position].typeDeCase){
                    case 1:{
                        special += "Le joueur " + joueur[tourJoueur(tour)].name + " à gagné";
                        victoire = true;
                        break;
                    }

                    case 2:{
                        joueur[tourJoueur(tour)].position = position(des, tour);
                        special += "Vous êtes tombés sur une oie, vous doublez votre lancé";
                        break;
                    }

                    case 3:{
                        joueur[tourJoueur(tour)].position = 0;
			special += "Vous êtes tombé sur la tête de mort, vous retournez au départ";
                        break;
                    }

                    case 4:{
                        joueur[tourJoueur(tour)].nbAttente = 2;
                        special += "Vous êtes tombé sur la case Hôtel, vous devez attendre 2 tours";
                        break;
                    }

                    case 5:{
                        joueur[tourJoueur(tour)].position =
                                joueur[tourJoueur(tour)].position - 12;
                        special += "Vous êtes tombé sur la case 40, vous reculez jusqu'à la case 30";
                        break;
                    }

                    default:{
                        break;
                    }
                }

		colorP += joueur[tourJoueur(tour)].color;
		colorP += "Le joueur " + joueur[tourJoueur(tour)].name + " à fait " + des + ", il était à la case "
		    + joueur[tourJoueur(tour)].positionAvant + ", maintenant il se trouve sur la  "
		    + joueur[tourJoueur(tour)].position + ANSI_RESET;
		println(colorP + "\n");
		
		toString(plateau);
		println();

		special += ANSI_RESET;
		println(special + "\n");
		
            } else {
		println("Il vous reste " + joueur[tourJoueur(tour)].nbAttente + " tour.");
                joueur[tourJoueur(tour)].nbAttente = joueur[tourJoueur(tour)].nbAttente - 1;
            }
	    
            tour = !tour;
	    
        }while(!victoire);
    }

    void initJoueurs(){
        for (int i = 0; i < length(joueur); i++){
            joueur[i] = new Joueur();
            println("Le joueur " + (i+1) + " est :");
            joueur[i].name = readString();
        }
	joueur[0].color = ANSI_BLUE;
	joueur[1].color = ANSI_CYAN;
    }

    void initCase(){
        for (int i = 0; i < length(plateau); i++){
            plateau[i] = new Case();
        }
        plateau[63].typeDeCase = 1;
        plateau[5].typeDeCase = 2;
        plateau[9].typeDeCase = 2;
        plateau[14].typeDeCase = 2;
        plateau[18].typeDeCase = 2;
        plateau[23].typeDeCase = 2;
        plateau[27].typeDeCase = 2;
        plateau[32].typeDeCase = 2;
        plateau[36].typeDeCase = 2;
        plateau[41].typeDeCase = 2;
        plateau[45].typeDeCase = 2;
        plateau[50].typeDeCase = 2;
        plateau[54].typeDeCase = 2;
        plateau[59].typeDeCase = 2;
        plateau[58].typeDeCase = 3;
        plateau[19].typeDeCase = 4;
        plateau[42].typeDeCase = 5;

	for (int i = 0; i < length(plateau); i++){
	    switch (plateau[i].typeDeCase){
	    case 1:{
		plateau[i].color = ANSI_RED;
		break;
	    }
		
	    case 2:{
		plateau[i].color = ANSI_WHITE + ANSI_UNDERLINE;
		break;
	    }
		
	    case 3:{
		plateau[i].color = ANSI_BLACK;
		break;
	    }

	    case 4:{
		plateau[i].color = ANSI_GREEN;
		break;
	    }

	    case 5:{
		plateau[i].color = ANSI_PURPLE;
		break;
	    }
		
	    default:{
		plateau[i].color = ANSI_YELLOW;
		break;
	    }
		
	    }
	}
    }

    void toString(Case[] c){
        String dis = "";

        for (int i = 0; i < length(plateau); i++) {
	    dis += " | ";
	    dis += plateau[i].color;
	    
            if (joueur[0].position != i && joueur[1].position != i) {
                dis += "#-#";
            } else {
                if (joueur[0].position != joueur[1].position){
                    for (int j = 0; j < length(joueur); j++){
                        if (joueur[j].position == i){
                            if (j == 0){
                                dis += "1-#";
                            } else {
                                dis += "#-2";
                            }
                        }
                    }
                } else {
                    dis += "1-2";
                }
            }
	    
	    dis += ANSI_RESET;
        }
	
        println(dis);
    }

    int position(int des, boolean tour){
        int res = 0;
        int tailleP = length(plateau) - 1;

        if ((joueur[tourJoueur(tour)].position + des) > tailleP){
            res = tailleP - (des - (tailleP - joueur[tourJoueur(tour)].position));

            return res;
        }

        return joueur[tourJoueur(tour)].position + des;
    }

    int lancerDes(){
        return 1 + (int)(random() * 6);
    }

    int tourJoueur(boolean tour){
        if (tour){
            return 1;
        }
        return 0;
    }


    String[][] toString(CSVFile csv){
        String[][] questions = new String[rowCount(csv)][columnCount(csv)];

        for (int i = 0; i < length(questions, 1); i++){
            for (int j = 0; j < length(questions, 2); j++){
                questions[i][j] = getCell(csv, i, j);
            }
        }

        return questions;
    }

    void println(String[][] tab){
        for (int i = 0; i < length(tab, 1); i++){
            for (int j = 0; j < length(tab, 2); j++){
                print(tab[i][j] + " ");
            }
            println();
        }
    }

    void println(String[] tab){
        for (int i = 0; i < length(tab); i++){
                println(tab[i] + " ");
            }
    }
}
