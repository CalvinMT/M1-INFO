package test;

import document.TexteCentre;
import document.TexteDroite;
import document.TexteGauche;

public class Test {
	
	static void testTexteCentre () {
		System.out.println("##### TEST - TexteCentre #####");
		System.out.println();

		TexteGauche evenGauche = new TexteGauche("even");
		TexteGauche evenOddGauche = new TexteGauche("even");
		TexteGauche oddGauche = new TexteGauche("odd");
		TexteGauche oddOddGauche = new TexteGauche("odd");
		TexteGauche evenLongGauche = new TexteGauche("This is a very long even text.");
		TexteGauche oddLongGauche = new TexteGauche("This is a very long odd text.");

		TexteCentre evenCentre = new TexteCentre("even");
		TexteCentre evenOddCentre = new TexteCentre("even");
		TexteCentre oddCentre = new TexteCentre("odd");
		TexteCentre oddOddCentre = new TexteCentre("odd");
		TexteCentre evenLongCentre = new TexteCentre("This is a very long even text.");
		TexteCentre oddLongCentre = new TexteCentre("This is a very long odd text.");

		TexteDroite evenDroite = new TexteDroite("even");
		TexteDroite evenOddDroite = new TexteDroite("even");
		TexteDroite oddDroite = new TexteDroite("odd");
		TexteDroite oddOddDroite = new TexteDroite("odd");
		TexteDroite evenLongDroite = new TexteDroite("This is a very long even text.");
		TexteDroite oddLongDroite = new TexteDroite("This is a very long odd text.");

		evenGauche.setSeparateur("-");
		evenOddGauche.setSeparateur("-");
		oddGauche.setSeparateur("-");
		oddOddGauche.setSeparateur("-");
		evenLongGauche.setSeparateur("-");
		oddLongGauche.setSeparateur("-");

		evenCentre.setSeparateur("-");
		evenOddCentre.setSeparateur("-");
		oddCentre.setSeparateur("-");
		oddOddCentre.setSeparateur("-");
		evenLongCentre.setSeparateur("-");
		oddLongCentre.setSeparateur("-");

		evenDroite.setSeparateur("-");
		evenOddDroite.setSeparateur("-");
		oddDroite.setSeparateur("-");
		oddOddDroite.setSeparateur("-");
		evenLongDroite.setSeparateur("-");
		oddLongDroite.setSeparateur("-");

		evenGauche.fixeLargeur(10);
		evenOddGauche.fixeLargeur(9);
		oddGauche.fixeLargeur(10);
		oddOddGauche.fixeLargeur(9);
		evenLongGauche.fixeLargeur(10);
		oddLongGauche.fixeLargeur(10);

		evenCentre.fixeLargeur(10);
		evenOddCentre.fixeLargeur(9);
		oddCentre.fixeLargeur(10);
		oddOddCentre.fixeLargeur(9);
		evenLongCentre.fixeLargeur(10);
		oddLongCentre.fixeLargeur(10);

		evenDroite.fixeLargeur(10);
		evenOddDroite.fixeLargeur(9);
		oddDroite.fixeLargeur(10);
		oddOddDroite.fixeLargeur(9);
		evenLongDroite.fixeLargeur(10);
		oddLongDroite.fixeLargeur(10);
		
		System.out.println(evenGauche.toString());
		System.out.println(evenOddGauche.toString());
		System.out.println(oddGauche.toString());
		System.out.println(oddOddGauche.toString());
		System.out.println(evenLongGauche.toString());
		System.out.println(oddLongGauche.toString());

		System.out.println(evenCentre.toString());
		System.out.println(evenOddCentre.toString());
		System.out.println(oddCentre.toString());
		System.out.println(oddOddCentre.toString());
		System.out.println(evenLongCentre.toString());
		System.out.println(oddLongCentre.toString());

		System.out.println(evenDroite.toString());
		System.out.println(evenOddDroite.toString());
		System.out.println(oddDroite.toString());
		System.out.println(oddOddDroite.toString());
		System.out.println(evenLongDroite.toString());
		System.out.println(oddLongDroite.toString());
		
		System.out.println();
		System.out.println();
	}
	
	

	public static void main(String[] args) {
		testTexteCentre();
	}

}
