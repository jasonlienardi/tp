# User Guide: FitNUS
## Project Introduction
FitNUS is a CLI application that aims to help combat diabetes and the overconsumption of calories, sugar, and
carbohydrates. Our vision is to promote healthy lifestyle amongst NUS students.

Users are able to track the meals, drinks, and exercises they have in a day and even past records. 

## Setup 
To use the app please follow the setup procedures below:
1. Download the JAR file. 
2. Place it into an empty folder. 
3. Navigate to the folder you just created.
4. Run the JAR file.

Note:
1. All files under 'data' and 'db' folders should not be modified by user
2. Enter "exit" to properly close the program and save the data

## Table of Contents
<!-- TOC -->
* [User Guide: FitNUS](#user-guide-fitnus)
  * [Project Introduction](#project-introduction)
  * [Setup](#setup-)
  * [Table of Contents](#table-of-contents)
  * [1) Features List](#1-features-list)
    * [1.1 Information for users](#11-information-for-users)
    * [1.1.1 Viewing all commands: `help`](#111-viewing-all-commands-help)
    * [1.1.2 Viewing all pre-defined meals: `allMeals`](#112-viewing-all-pre-defined-meals-allmeals)
    * [1.1.3 Viewing all pre-defined drinks: `allDrinks`](#113-viewing-all-pre-defined-drinks-alldrinks)
    * [1.1.4 Viewing all pre-defined exercises: `allExercises`](#114-viewing-all-pre-defined-exercises-allexercises)
    * [1.2 For user to add data](#12-for-user-to-add-data)
    * [1.2.1 Add a meal eaten: `eat`](#121-add-a-meal-eaten-eat)
    * [1.2.2 Add a drink: `drink`](#122-add-a-drink-drink)
    * [1.2.2.1 Add water: `drink d/water`](#1221-add-water-drink-dwater)
    * [1.2.3 Add exercise: `exercise`](#123-add-exercise-exercise)
    * [1.2.4 Add new meal to available meals: `newMeal`](#124-add-new-meal-to-available-meals-newmeal)
    * [1.2.4 Add new meal to available meals: `newDrink`](#124-add-new-meal-to-available-meals-newdrink)
    * [1.2.5 Add new exercise to available exercises: `newExercise`](#125-add-new-exercise-to-available-exercises-newexercise)
  * [1.3 For data retrieval](#13-for-data-retrieval)
    * [1.3.1 Find the information about a certain meal: `infoMeal`](#131-find-the-information-about-a-certain-meal-infomeal)
    * [1.3.2 Find the information about a certain drink: `infoDrink`](#132-find-the-information-about-a-certain-drink-infodrink)
    * [1.3.3 Find the information about a certain exercise: `infoExercise`](#133-find-the-information-about-a-certain-exercise-infoexercise)
    * [1.3.4 View daily calories consumed: `calories`](#134-view-daily-calories-consumed-calories)
    * [1.3.5 View daily carbohydrates consumed: `carbs`](#135-view-daily-carbohydrates-consumed-carbs)
    * [1.3.6 View daily proteins consumed: `protein`](#136-view-daily-proteins-consumed-protein)
    * [1.3.7 View daily fat consumed: `fat`](#137-view-daily-fat-consumed-fat)
    * [1.3.8 View daily sugar consumed: `sugar`](#138-view-daily-sugar-consumed-sugar)
    * [1.3.9 View daily fiber consumed: `fiber`](#139-view-daily-fiber-consumed-fiber)
    * [1.3.10 View daily water consumption: `viewWater`](#1310-view-daily-water-consumption-viewwater)
    * [1.3.11 View daily calories consumed: `caloriesBurnt`](#1311-view-daily-calories-consumed-caloriesburnt)
    * [1.3.12 View daily calories and water intake recommendation: `recommend`](#1312-view-daily-calories-and-water-intake-recommendation-recommend)
  * [1.4 For listing arrays](#14-for-listing-arrays)
    * [1.4.1 List today's meal intake: `listMeals`](#141-list-todays-meal-intake-listmeals)
    * [1.4.2 List today's drink intake: `listDrinks`](#142-list-todays-drink-intake-listdrinks)
    * [1.4.3 List today's exercises done: `listExercises`](#143-list-todays-exercises-done-listexercises)
    * [1.4.4 List everything inputted today: `listEverything`](#144-list-everything-inputted-today-listeverything)
    * [1.4.5 List entire app's lifecycle meals intake: `listMealsAll`](#145-list-entire-apps-lifecycle-meals-intake-listmealsall)
    * [1.4.6 List entire app's lifecycle drinks intake: `listDrinksAll`](#146-list-entire-apps-lifecycle-drinks-intake-listdrinksall)
    * [1.4.7 List entire app's lifecycle exercises done: `listExercisesAll`](#147-list-entire-apps-lifecycle-exercises-done-listexercisesall)
    * [1.4.8 List everything inputted for the entire app's lifecycle: `listEverythingAll`](#148-list-everything-inputted-for-the-entire-apps-lifecycle-listeverythingall)
    * [1.4.9 List meal intake for a certain date: `listMeals d/[DATE]`](#149-list-meal-intake-for-a-certain-date-listmeals-ddate)
    * [1.4.10 List drink intake for a certain date: `listDrinks d/[DATE]`](#1410-list-drink-intake-for-a-certain-date-listdrinks-ddate)
    * [1.4.11 List exercise done for a certain date: `listExercises d/[DATE]`](#1411-list-exercise-done-for-a-certain-date-listexercises-ddate)
    * [1.4.12 List entire food intake and exercise done for a certain date: `listEverything d/[DATE]`](#1412-list-entire-food-intake-and-exercise-done-for-a-certain-date-listeverything-ddate)
  * [1.5 For editing existing data](#15-for-editing-existing-data)
    * [1.5.1 Edit an existing meal after inserted: `editMeal`](#151-edit-an-existing-meal-after-inserted-editmeal)
    * [1.5.2 Edit an existing drink after inserted: `editDrink`](#152-edit-an-existing-drink-after-inserted-editdrink)
    * [1.5.3 Edit water intake after inserted: `editWater`](#153-edit-water-intake-after-inserted-editwater)
  * [1.6 For deleting data](#16-for-deleting-data)
    * [1.6.1 Delete certain meal entry: `deleteMeal`](#161-delete-certain-meal-entry-deletemeal)
    * [1.6.2 Delete certain drink entry: `deleteDrink`](#162-delete-certain-drink-entry-deletedrink)
  * [1.7 For clearing data](#17-for-clearing-data)
    * [1.7.1 Clear all entries: `clear`](#171-clear-all-entries-clear)
  * [1.8: Exit program](#18-exit-program)
    * [1.8.1 Exit the app: `exit`](#181-exit-the-app-exit)
<!-- TOC -->

## 1) Features List
### 1.1 Information for users
### 1.1.1 Viewing all commands: `help`
Shows a list of all possible command inputs.  
**Format**: `help`   
**Expected Output**:
~~~
Here's all the valid commands I recognise: 

Track a meal/drink/exercise: 
- Track a meal eaten: eat m/MEAL s/SERVING_SIZE
- Track a drink: drink d/DRINK s/VOLUME(ML)
- Track an exercise: exercise e/EXERCISE d/DURATION(MINUTES) i/INTENSITY(HIGH, MEDIUM, LOW)

View available meals/drinks/exercises: 
- View all meals that you can input: allMeals
- View all drinks that you can input: allDrinks
- View all exercises that you can input: allExercises

Find information about a meal/drink/exercise: 
- Find the information about a certain meal: infoMeal MEAL
- Find the information about a certain drink: infoDrink DRINK
- Find the information about a certain exercise: infoExercise EXERCISE

View nutrients and calories: 
- View daily calories consumed: calories
- View daily carbohydrates consumed: carbs
- View daily proteins consumed: protein
- View daily fat consumed: fat
- View daily sugar consumed: sugar
- View daily fiber consumed: fiber
- View daily water consumption: viewWater
- View daily calories burnt: caloriesBurnt

List Commands: 
- List today's meal intake: listMeals
- List today's drink intake: listDrinks
- List today's exercises done: listExercises
- List today's entire food intake and exercises: listEverything
- List all meal intake: listMealsAll
- List all drink intake: listDrinksAll
- List all exercises done: listExercisesAll
- List all food intake and exercises: listEverythingAll
- List meal intake for certain date: listMeals d/dd-MM-yyyy
- List drink intake for certain date: listDrinks d/dd-MM-yyyy
- List exercises done for certain date: listExercises d/dd-MM-yyyy
- List entire food intake and exercises for certain date: listEverything d/dd-MM-yyyy

Edit Commands: 
- Edit an existing meal after inserted: editMeal INDEX s/NEW_SERVING_SIZE
- Edit an existing drink after inserted: editDrink INDEX s/NEW_SERVING_SIZE
- Edit total water intake after inserted: editWater s/TOTAL_WATER_INTAKE

Delete Commands: 
- Delete certain meal entry: deleteMeal INDEX
- Delete certain drink entry: deleteDrink INDEX
- Delete certain exercise entry: deleteExercise INDEX

Adding a meal/drink/exercises to available list: 
- Add a new meal to available meals: newMeal MEAL_NAME,CALORIES,CARBS,PROTEIN,FAT,FIBER,SUGAR
- Add a new drink to available drinks: newDrink DRINK_NAME,CALORIES,CARBS,SUGAR,PROTEIN,FAT
- Add a new exercise to available exercises: newExercise EXERCISE_NAME,CALORIES_BURNT_HIGH,CALORIES_BURNT_MEDIUM,CALORIES_BURNT_LOW

Miscellaneous: 
- View daily calories and water intake recommendation: recommend
- Clear all entries: clear
- Exit the app: exit 
~~~
### 1.1.2 Viewing all pre-defined meals: `allMeals`
Shows a list of all pre-defined meals. These meals will have their nutritional content defined per serving size and can
be inputted immediately.  
**Format**: `allMeals`   
**Expected Output**:
~~~
Available meals: 
- pizza
- chicken rice
- fried rice
- kaya toast
- laksa

You may also input a meal that isn't here.
~~~

### 1.1.3 Viewing all pre-defined drinks: `allDrinks`
Shows a list of all pre-defined drinks. These drinks will have their nutritional content defined per 100ml
and can be inputted immediately.  
**Format**: allDrinks
**Expected Output**:
~~~
Available drinks: 
- sprite
- milo
- iced lemon tea
- kopi

You may also input a drink that isn't here.
~~~

### 1.1.4 Viewing all pre-defined exercises: `allExercises`
Shows a list of all pre-defined exercises. These exercises will have the number of calories burnt for a
high/medium/low intensity workout defined per minute and can be inputted immediately.  
**Format**: allExercises
**Expected Output**:
~~~
Available exercises: 
- running
- swimming
- cycling

You may also input an exercise that isn't here.
~~~

### 1.2 For user to add data
### 1.2.1 Add a meal eaten: `eat`
Adds a meal to the list of meals  
**Format**: `eat m/MEAL s/SERVING_SIZE`  
**Sample Input**: `eat m/Chicken Rice s/1`  
**Expected Output**: 
~~~
Added 1 serving of chicken rice
~~~

### 1.2.2 Add a drink: `drink`
Adds a drink to the list of drinks  
**Format**: `drink d/DRINK s/VOLUME(ML)`  
**Sample Input**: `drink d/Iced Lemon Tea s/200`  
**Expected Output**: 
~~~
Added 200 ml of iced lemon tea
~~~

### 1.2.2.1 Add water: `drink d/water`
Adds water to the list of water  
**Format**: `drink d/water s/SERVING_SIZE`  
**Sample Input**: `drink d/water s/100`  
**Expected Output**:
~~~
Added 100ml of water
~~~

### 1.2.3 Add exercise: `exercise`
Adds exercise to the list of exercises done  
**Format**: `exercise e/EXERCISE d/DURATION(MINUTES) i/INTENSITY(HIGH, MEDIUM, LOW)`  
**Sample Input**: `exercise e/swimming d/30 i/HIGH`  
**Expected Output**:
~~~
Tracked 30 minutes of swimming
~~~

### 1.2.4 Add new meal to available meals: `newMeal`
Adds a new meal to available meals  
**Format**: `newMeal MEAL_NAME,CALORIES,CARBS,PROTEIN,FAT,FIBER,SUGAR`  
**Sample Input**: `newMeal mie,607,75,25,23,2,10`  
**Expected Output**:
~~~
Added mie to available meals
~~~

### 1.2.4 Add new meal to available meals: `newDrink`
Adds a new drink to available drinks  
**Format**: `newDrink DRINK_NAME,CALORIES,CARBS,SUGAR,PROTEIN,FAT`   
**Sample Input**: `newDrink coke,153,32,1,2,1`   
**Expected Output**:
~~~
Added coke to available drinks
~~~

### 1.2.5 Add new exercise to available exercises: `newExercise`
Adds a new exercise to available exercises  
**Format**: `newExercise EXERCISE_NAME,CALORIES_BURNT_HIGH,CALORIES_BURNT_MEDIUM,CALORIES_BURNT_LOW`   
**Sample Input**: `newExercise badminton,20,10,5`  
**Expected Output**:
~~~
Added badminton to available exercises
~~~
 
## 1.3 For data retrieval
### 1.3.1 Find the information about a certain meal: `infoMeal`
For the specified meal, display its nutritional content to the user  
**Format**: `infoMeal MEAL`  
**Sample Input**: `infoMeal chicken rice`  
**Expected Output**:  
~~~
Meal: chicken rice (per serving)`
Calories: 400
Carbs: 50
Protein: 30
Fat: 20
Fiber: 10
Sugar: 5
Sugar: 5
~~~

### 1.3.2 Find the information about a certain drink: `infoDrink`
For the inputted drink, display its nutritional content to the user  
**Format**: `infoDrink DRINK`  
**Sample input**: `infoDrink milo`  
**Expected output**:    
~~~
Drink: milo (100 ml)
Calories: 124
Carbs: 20
Sugar: 3
Protein: 3
Fat: 1
~~~

### 1.3.3 Find the information about a certain exercise: `infoExercise`
For the inputted exercise, display its calories burnt per minute for different intensities to the user   
**Format**: `infoExercise EXERCISE`   
**Sample input**: `infoExercise swimming`   
**Expected output**:    
~~~
Exercise: swimming
~ Calories burnt for a 1 minute workout of ~
HIGH intensity: 12
MEDIUM intensity: 8
LOW intensity: 5
~~~

### 1.3.4 View daily calories consumed: `calories`
Display current total calorie intake for the day   
**Format**: `calories`    
**Expected output**: 
~~~
Total Calories: 100
~~~

### 1.3.5 View daily carbohydrates consumed: `carbs`
Display current total carbohydrates intake for the day  
**Format**: `carbs`  
**Expected output**: 
~~~
Total Carbohydrates: 150 grams
~~~

### 1.3.6 View daily proteins consumed: `protein`
Display current total protein intake for the day  
**Format**: `protein`  
**Expected output**: 
~~~
Total Proteins: 100 grams
~~~

### 1.3.7 View daily fat consumed: `fat`
Display current total fat intake for the day  
**Format**: `fat`  
**Expected output**: 
~~~
Total Fat: 50 grams
~~~

### 1.3.8 View daily sugar consumed: `sugar`
Display current total sugar intake for the day  
**Format**: `sugar`  
**Expected output**: 
~~~
Total Sugar: 20 grams
~~~

### 1.3.9 View daily fiber consumed: `fiber`
Display current total fiber intake (g) for the day  
**Format**: `fiber`  
**Expected output**: 
~~~
Total Fiber: 20 grams
~~~

### 1.3.10 View daily water consumption: `viewWater`
Display current total water intake (in ml) for the day  
**Format**: `viewWater`  
**Expected output**: 
~~~
Total water intake today: 0 ml
~~~

### 1.3.11 View daily calories consumed: `caloriesBurnt`
Display current total calorie burnt for the day   
**Format**: `caloriesBurnt`    
**Expected output**: 
~~~
Total calories burnt: 70
~~~

### 1.3.12 View daily calories and water intake recommendation: `recommend`
Display today's recommended water and calories intake  
**Format**: `recommend`    
**Expected output**:
~~~
Great! You are on track with the water intake!
  ~~
Recommend eating more food. Please eat 500 more calories
~~~

## 1.4 For listing arrays
### 1.4.1 List today's meal intake: `listMeals`
Display all the meals user input today   
**Format**: `listMeals`   
**Expected output**:
~~~
here's what you have eaten today
1. chicken rice (serving size: 1) | date: 01-04-2024
~~~

### 1.4.2 List today's drink intake: `listDrinks`
Display all the drinks user input today  
**Format**: `listDrinks`  
**Expected output**:  
~~~
here's what you have drank today
1. sprite (volume: 100ml) | date: 01-04-2024

Total water intake today: 0 ml
~~~

### 1.4.3 List today's exercises done: `listExercises`
Display all the exercises user tracked today  
**Format**: `listExercises`  
**Expected output**: 
~~~
here's the exercises you've done today
1. boxing | duration: 10 | intensity: LOW | date: 01-04-2024
~~~

### 1.4.4 List everything inputted today: `listEverything`
Display all the meals, drinks and exercises user tracked today  
**Format**: `listEverything`  
**Expected output**:  
~~~
here's what you have consumed today
1. chicken rice (serving size: 1) | date: 01-04-2024
2. sprite (volume: 100ml) | date: 01-04-2024

Total water intake today: 100 ml
       ~~~
here's the exercises you've done today
1. boxing | duration: 10 | intensity: LOW | date: 01-04-2024
~~~

### 1.4.5 List entire app's lifecycle meals intake: `listMealsAll`
Display all the meals user inputted for the entire app lifecycle   
**Format**: `listMealsAll`   
**Expected output**:   
~~~
here's what you have eaten so far
1. mala (serving size: 2) | date: 30-03-2024
2. chicken rice (serving size: 1) | date: 01-04-2024
~~~

### 1.4.6 List entire app's lifecycle drinks intake: `listDrinksAll`
Display all the drinks user inputted for the entire app lifecycle  
**Format**: `listDrinksAll`  
**Expected output**:  
~~~
here's what you have drank so far
1. milo dinosaur (volume: 200ml) | date: 30-03-2024
2. sprite (volume: 100ml) | date: 01-04-2024

Total water intake today: 100 ml
~~~

### 1.4.7 List entire app's lifecycle exercises done: `listExercisesAll`
Display all the exercises inputted for the entire app lifecycle  
**Format**: `listExercisesAll`  
**Expected output**:  
~~~
here's the exercises you've done so far
1. cycling | duration: 100 | intensity: LOW | date: 29-02-2024
2. swimming | duration: 100 | intensity: HIGH | date: 30-03-2024
3. boxing | duration: 10 | intensity: LOW | date: 01-04-2024
~~~

### 1.4.8 List everything inputted for the entire app's lifecycle: `listEverythingAll`
Display all the drinks, meals, and exercises inputted for the entire app lifecycle  
**Format**: `listEverythingAll`  
**Expected output**:  
~~~
here's what you have consumed so far
1. mala (serving size: 2) | date: 30-03-2024
2. chicken rice (serving size: 1) | date: 01-04-2024
3. milo dinosaur (volume: 200ml) | date: 30-03-2024
4. sprite (volume: 100ml) | date: 01-04-2024

Total water intake today: 100 ml
       ~~~
here's the exercises you've done so far
1. cycling | duration: 100 | intensity: LOW | date: 29-02-2024
2. swimming | duration: 100 | intensity: HIGH | date: 30-03-2024
3. boxing | duration: 10 | intensity: LOW | date: 01-04-2024
~~~

### 1.4.9 List meal intake for a certain date: `listMeals d/[DATE]`
Display all the meals user inputted for the specified date  
**Format**: `listMeals d/dd-MM-yyyy`  
**Expected output**:
~~~
here's what you have eaten on 01-04-2024
1. soup kambeng (serving size: 2) | date: 01-04-2024
2. nasi lemak (serving size: 2) | date: 01-04-2024
3. tau huay (serving size: 1) | date: 01-04-2024
4. roti prata (serving size: 2) | date: 01-04-2024
~~~

### 1.4.10 List drink intake for a certain date: `listDrinks d/[DATE]`
Display all the drinks user inputted for the specified date (excluding water)  
**Format**: `listDrinks d/dd-MM-yyyy`  
**Expected output**:
~~~
here's what you have drank on 01-04-2024
1. honey lemon tea (volume: 200ml) | date: 01-04-2024
2. kopi c (volume: 500ml) | date: 01-04-2024
3. milo (volume: 200ml) | date: 01-04-2024
~~~

### 1.4.11 List exercise done for a certain date: `listExercises d/[DATE]`
Display all the exercises user inputted for the specified date  
**Format**: `listExercises d/dd-MM-yyyy`  
**Expected output**:
~~~
here's the exercises you've done on 01-04-2024
1. boxing | duration: 10 | intensity: LOW | date: 01-04-2024
~~~

### 1.4.12 List entire food intake and exercise done for a certain date: `listEverything d/[DATE]`
Display all the meals, drinks, and exercises user inputted for the specified date  
**Format**: `listEverything d/dd-MM-yyyy`  
**Expected output**:
~~~
here's what you have consumed on 01-04-2024
1. soup kambeng (serving size: 2) | date: 01-04-2024
2. nasi lemak (serving size: 2) | date: 01-04-2024
3. tau huay (serving size: 1) | date: 01-04-2024
4. roti prata (serving size: 2) | date: 01-04-2024
5. honey lemon tea (volume: 200ml) | date: 01-04-2024
6. kopi c (volume: 500ml) | date: 01-04-2024
7. milo (volume: 200ml) | date: 01-04-2024

       ~~~
here's the exercises you've done on 01-04-2024
1. boxing | duration: 10 | intensity: LOW | date: 01-04-2024
~~~

## 1.5 For editing existing data
### 1.5.1 Edit an existing meal after inserted: `editMeal`
For a meal that was inputted in the day, edit its serving size  
**Format**: `editMeal INDEX s/NEW_SERVING_SIZE`  
**Sample input**: `editMeal 2 s/2`  
**Expected output**:
~~~
Pizza has been edited to 2 servings
~~~

### 1.5.2 Edit an existing drink after inserted: `editDrink`
For a drink that was inputted in the day, edit its serving size  
**Format**: `editDrink INDEX s/NEW_SERVING_SIZE`  
**Sample input**: editDrink 1 s/200  
**Expected output**:
~~~
Sprite has been edited to 200 ml
~~~

### 1.5.3 Edit water intake after inserted: `editWater`
Edit serving size of total water intake  
**Format**: `editWater s/TOTAL_WATER_INTAKE`  
**Sample input**: `editWater 200`  
**Expected output**: 
~~~
Total water has been edited to 200 ml
~~~

## 1.6 For deleting data
### 1.6.1 Delete certain meal entry: `deleteMeal`
For a meal that was inputted in the day, delete its input based on its index in the meal list
**Format**: `deleteMeal INDEX`
**Sample Input**: `deleteMeal 1`
**Expected output**:
~~~
Removed Chicken Rice From Meals
~~~

### 1.6.2 Delete certain drink entry: `deleteDrink`
For a drink that was inputted in the day, delete its input based on its index in the drink list  
**Format**: `deleteDrink INDEX`  
**Sample input**: `deleteDrink 1`  
**Expected output:**  
~~~
Removed Iced Lemon Tea From Drinks
~~~

## 1.7 For clearing data
### 1.7.1 Clear all entries: `clear`
Clear all entries in mealList and drinkList  
**Format**: `clear`  
**Expected output**:
~~~
All entries have been deleted
~~~

## 1.8: Exit program
### 1.8.1 Exit the app: `exit`
**Format**: `exit`  
**Expected output**:
~~~
Bye. Hope to see you again soon!
~~~
