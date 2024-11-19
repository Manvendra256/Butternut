# Flashcard Recommendation App

<details open="open">
<summary>Table of Contents</summary>

- [About](#about)
  - [Built With](#built-with)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Usage](#usage)
- [Acknowledgements](#acknowledgements)

</details>


## About

<table>
<tr>
<td>

This app helps people create digital flashcards and memorize information by using two proven techniques: Active recall and Spaced repetition. I built it as 
I couldn't find any other good and **free** alternatives in this category. It uses ***weighted random sampling*** to recommend the next flashcard to the user. The 
weight in this case is simply the users confidence level with that particular flashcard. There are three levels of heirarchy to aid in the organization of flashcards (the heirarchy is take from Brainscape). 
At the top level, we have a _class_. Under class, we have _decks_ and under a deck we have _flashcards_. For example **Geography** could be a _class_. Under **Geography**, 
we could have multiple decks, for example one for each country (let's assume **USA**). Under **USA**, we could have multiple flashcards containing questions/info around 
USA geography. 

Other apps in this category are:
 - AnkiDroid (free but [doesn't](https://www.google.com/imgres?q=ankidroid%20flashcards&imgurl=https%3A%2F%2Fcdn6.aptoide.com%2Fimgs%2Fb%2F2%2Fd%2Fb2d8d716f5f374cdd71a70b1931e66e7_screen.png&imgrefurl=https%3A%2F%2Fankidroid.en.aptoide.com%2Fapp&docid=hbOXchIYwGrBsM&tbnid=9xwQzcm83sHxrM&vet=12ahUKEwjy_7rGmeiJAxUqxjgGHUJZKDIQM3oFCIQBEAA..i&w=427&h=800&hcb=2&ved=2ahUKEwjy_7rGmeiJAxUqxjgGHUJZKDIQM3oFCIQBEAA) have a nice UX)
 - Brainscape (great UX but paid)
 - Duocard (only helps with word memoraization and is also paid) 

</td>
</tr>
</table>

### Built With

- Kotlin
- Jetpack Compose

## Visualisations
### DFS
![alt text](gifs/dfs.gif)

## Getting Started
### Prerequisites
An android mobile and android studio. This is not currently hosted on Playstore so people interested in trying it out will need to install it via android studio.
### Usage
#### Create a Class.

#### Create a Deck

#### Create a Flashcard

#### Revise all flashcards.

#### Revise flashcards within a class.

#### Revise flashcards within a deck.



## Acknowledgements
