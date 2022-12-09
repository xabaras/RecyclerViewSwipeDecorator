# RecyclerViewSwipeDecorator #
A simple utility class to add a background, an icon and a label to a RecyclerView item while swiping it left or right.

![Sample app - Swipe right](https://xabaras.github.io/RecyclerViewSwipeDecorator/img/screen01.png)
![Sample app - Swipe left](https://xabaras.github.io/RecyclerViewSwipeDecorator/img/screen02.png)

## How do I get set up? ##

In version 1.3 RecyclerViewSwipeDecorator migrated to [AndroidX](https://developer.android.com/jetpack/androidx/migrate)

### Latest Version ###

Get latest version via JitPack

[![](https://jitpack.io/v/xabaras/RecyclerViewSwipeDecorator.svg)](https://jitpack.io/#xabaras/RecyclerViewSwipeDecorator)

If you use Gradle add JitPack in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then add the dependency
```groovy
dependencies {
        implementation 'com.github.xabaras:RecyclerViewSwipeDecorator:1.4'
}
```

If you use Maven add the JitPack repository to your build file 
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then add the dependency
```xml
<dependency>
    <groupId>com.github.xabaras</groupId>
    <artifactId>RecyclerViewSwipeDecorator</artifactId>
    <version>1.4</version>
</dependency>
```

### Previous versions ~1.2.3 ###

Get it via Gradle

```groovy
implementation 'it.xabaras.android:recyclerview-swipedecorator:1.2.3'
```
or Maven
```xml
<dependency>
  <groupId>it.xabaras.android</groupId>
  <artifactId>recyclerview-swipedecorator</artifactId>
  <version>1.2.3</version>
  <type>pom</type>
</dependency>
```

**Currently working to bring latest version to Maven Central.**

## Usage ##
Here is a non-comprehensive guide to RecyclerViewSwipeDecorator for any further information you can reference the library [javadoc](https://xabaras.github.io/RecyclerViewSwipeDecorator/javadoc/), the sources and/or the sample app sources.

Create an ItemTouchHelper.SimpleCallback, instantiate an ItemTouchHelper with this callback and attach it to your RecyclerView
```java
ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // Take action for the swiped item
    }
};
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
itemTouchHelper.attachToRecyclerView(recyclerView);
```

Override the onChildDrawn method of your SimpleCallback object
```java
@Override
public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
}
```

Create a RecyclerViewSwipeDecorator using the RecyclerViewSwipeDecoratorBuilder and call the decorate() method
```java
public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){

    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.my_background))
            .addActionIcon(R.drawable.my_icon)
            .create()
            .decorate();
            
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
}
```

#### Customizing ####
You can choose different background/icon combinations for left and right swipe directions by using direction specific methods in the Builder object.
If you want you can add a label for each swiping direction or setting the icon tint.

A method to set the action icon margin from the view left/right bound it available too.

##### public Builder addBackgroundColor(int color) #####
    Add a background color to either (left/right) swipe directions

##### public Builder addActionIcon(int color) #####
    Add an action icon to either (left/right) swipe directions

##### public Builder setActionIconTint(int color) #####
    Set the tint color for either (left/right) action icons

##### public Builder addCornerRadius(int unit, float size) ##### :new:
    Add a corner radius to swipe background for either (left/right) swipe directions

##### public Builder addPadding(int unit, float top, float side, float bottom) ##### :new:
    Add padding to the swipe background for either (left/right) swipe directions

##### public Builder addSwipeRightBackgroundColor(int color) #####
    Add a background color to the view while swiping right.

##### public Builder addSwipeRightActionIcon(int color) #####
    Add an action icon while swiping right (it's suggested to use 24dp square vector drawables.).

##### public Builder setSwipeRightActionIconTint(int color) #####
    Set the tint color for action icon shown while swiping right

##### public Builder addSwipeLeftBackgroundColor(int color) #####
    Add a background color to the view while swiping left.

##### public Builder addSwipeLeftActionIcon(int color) #####
    Add an action icon while swiping left (it's suggested to use 24dp square vector drawables.).

##### public Builder setSwipeLeftActionIconTint(int color) #####
    Set the tint color for action icon shown while swiping left

##### public Builder setIconHorizontalMargin(int unit, int iconHorizontalMargin) #####
    Set icon horizontal margin from left/right bound of the view (default is 16dp).
    Unit is a TypedValue (e.g. TypedValue.COMPLEX_UNIT_DIP)

##### public Builder addSwipeRightLabel(String label) #####
    Add a label to be shown while swiping right

##### public Builder setSwipeRightLabelColor(int color) #####
    Set the color of the label to be shown while swiping right

##### public Builder setSwipeRightLabelTextSize(int unit, float size) #####
    Set the size of the label to be shown while swiping right
    Unit is a TypedValue (e.g. TypedValue.COMPLEX_UNIT_SP)

##### public Builder setSwipeRightLabelTypeface(Typeface typeface) #####
    Set the Typeface of the label to be shown while swiping right


##### public Builder addSwipeLeftLabel(String label) #####
    Add a label to be shown while swiping left

##### public Builder setSwipeLeftLabelColor(int color) #####
    Set the color of the label to be shown while swiping left

##### public Builder setSwipeLeftLabelTextSize(int unit, float size) #####
    Set the size of the label to be shown while swiping left
    Unit is a TypedValue (e.g. TypedValue.COMPLEX_UNIT_SP)

##### public Builder setSwipeLeftLabelTypeface(Typeface typeface) #####
    Set the Typeface of the label to be shown while swiping left

##### public Builder addSwipeLeftCornerRadius(int unit, float size) ##### :new:
    Add a corner radius to swipe background for left swipe direction

##### public Builder addSwipeRightCornerRadius(int unit, float size) ##### :new:
    Add a corner radius to swipe background for right swipe direction

##### public Builder addSwipeLeftPadding(int unit, float top, float right, float bottom) ##### :new:
    Add a corner radius to swipe background for right swipe direction

##### public Builder addSwipeRightPadding(int unit, float top, float left, float bottom) ##### :new:
    Add a corner radius to swipe background for left swipe direction

