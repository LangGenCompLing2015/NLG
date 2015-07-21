# NLG
Dieser Code entstand im Rahmen des Seminars "Introduction to the Computational Linguistics. Teil II" der Universtität Bremen im Sommersemester 2015. 
Die Aufgabe war, einen Sprachgenerierer für Rezeptanwendungen zu entwickeln.
Der javabasierte Code arbeitet mit simpleNLG (https://code.google.com/p/simplenlg/).

Der vorliegende Code kann drei Arten von Sätzen ausgeben:
1) Zutatenlisten
2) Rezeptanweisungen
3) Fehlermeldungen

Für den jeweiligen Satztyp wird jeweils eine andere Form des Inputs benötigt:

1) ingredients:#AnzahlZutat1;#EinheitZutat1;#Zutat1;#AnzahlZutat2;#EinheitZutat2;#Zutat2;...#AnzahlZutatN;#EinheitZutatN;#ZutatN; 
Bsp: ingredients:2,slice,tomato;1,,toast;;

2) instructions:#Anweisungsnummer:#Aktivität;#AnzahlAgens;#EinheitAgens;#Agens;#AnzahlPatient;#EinheitPatient;#Patient;;
Bsp: instructions:1/7:placing;2,slice,tomato;1,,toast;;

3) error:#WiederholungDesFehlers
Bsp: error:3

Quelle: https://github.com/LangGenCompLing2015/NLG
