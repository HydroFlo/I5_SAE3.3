# SAE 3.02 Classification K-NN

## Fonctionalitées
- Importer des données depuis un fichier CSV
- Visualiser les données dans un repère orthonormé
- Choisir les axes du repère
- Ajouter des données inconnues
- Déterminer le type d'un point à l'aide de l'algorithme KNN depuis la distance souhaitée par l'utilisateur
- Interface utilisateur
- Généricité

## Conception

Le programme repose sur un modèle MVC.

## Compilation et lancement du programe

Le programme se compile et se lance avec maven depuis la commande ``mvn javafx:run`` dans le dossier ``classiflex``.

## Jalon 1
### Contributions :

Florian Gambirasio :

    Création du model, des Iris, restructuration du projet pour Maven, création des classes de l'algo K-nn

Arthur Goddefroy :
        
    Création de l'IHM de base, visuel pour l'ajout de point dans l'IHM

Antoine Lencel :
        
    Choix des CSV dans l'IHM, gestion des erreurs dans l'IHM.

Mathieu Poumaere :

    Test, rédaction du dossier d'analyse du projet, fonction utilitaire

Cristobal Pinto :

    Amélioration de l'IHM

### A faire pour le Jalon 2 !

- Florian et Arthur: 
    
    Mise en place d'une classe abstraite afin de permettre l'utililisation de plusieurs type de données et pas juste des Iris

- Mathieu :

    Écrire les tests et refractor

Cristobal et Antoine :

    Refonte et ajout de composants dans l'IHM

## Jalon 2
### Contributions total :

- Mise en place du modèle : 

        Florian, Arthur et Mathieu
- Création des tests :

        Mathieu
- Implémentation de l'algorithme K-NN : 

        Florian
- Structuration du projet : 

        Florian
- Création de l'IHM : 

        Arthur (et amélioration par Antoine et Cristobal)
- Fonction utilitaire : 

        Mathieu
- Généralisation du code pour fonctionner avec plusieurs données : 

        Florian et Arthur
- Calcul de la robustesse selon le k choisi : 

        Arthur
- Chargement des données : 

        Antoine
- Écriture du README : 

        Florian et Cristobal

    

#### By : GAMBIRASIO Florian, GODDEFROY Arthur, PINTO Cristobal, POUMAERE Mathieu, LANCEL Antoine
