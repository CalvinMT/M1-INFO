package test;

import document.TexteCentre;

public class Test {
	
	static void testTexteCentre () {
		System.out.println("##### TEST - TexteCentre #####");
		System.out.println();

		TexteCentre even = new TexteCentre("even");
		TexteCentre evenOdd = new TexteCentre("even");
		TexteCentre odd = new TexteCentre("odd");
		TexteCentre oddOdd = new TexteCentre("odd");
		TexteCentre evenLong = new TexteCentre("This is a very long even text.");
		TexteCentre oddLong = new TexteCentre("This is a very long odd text.");

		even.setSeparateur("-");
		evenOdd.setSeparateur("-");
		odd.setSeparateur("-");
		oddOdd.setSeparateur("-");
		evenLong.setSeparateur("-");
		oddLong.setSeparateur("-");

		even.fixeLargeur(10);
		evenOdd.fixeLargeur(9);
		odd.fixeLargeur(10);
		oddOdd.fixeLargeur(9);
		evenLong.fixeLargeur(10);
		oddLong.fixeLargeur(10);

		System.out.println(even.texte());
		System.out.println(evenOdd.texte());
		System.out.println(odd.texte());
		System.out.println(oddOdd.texte());
		System.out.println(evenLong.texte());
		System.out.println(oddLong.texte());
		System.out.println(even.toString());
		System.out.println(evenOdd.toString());
		System.out.println(odd.toString());
		System.out.println(oddOdd.toString());
		System.out.println(evenLong.toString());
		System.out.println(oddLong.toString());
		
		System.out.println();
		System.out.println();
	}
	
	

	public static void main(String[] args) {
		testTexteCentre();
	}

}
