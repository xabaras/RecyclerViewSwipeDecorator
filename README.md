# RecyclerViewSwipeDecorator #
A simple utility class to add a background and/or an icon to a RecyclerView item while swiping it left or right.

[![Methods count and size](https://img.shields.io/badge/Methods%20and%20size-core:%2040%20|%20deps:%2021827%20|%2018%20KB-e91e63.svg)](http://www.methodscount.com/?lib=it.xabaras.android%3Arecyclerview-swipedecorator%3A1.0)

![Sample app - Swipe right](https://xabaras.github.io/RecyclerViewSwipeDecorator/img/screen01.png)
![Sample app - Swipe left](https://xabaras.github.io/RecyclerViewSwipeDecorator/img/screen02.png)

## How do I get set up? ##
Get it via Gradle
```groovy
compile 'it.xabaras.android:recyclerview-swipedecorator:1.0'
```
or Maven
```xml
<dependency>
  <groupId>it.xabaras.android</groupId>
  <artifactId>recyclerview-swipedecorator</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```

Or download the [latest AAR](https://bintray.com/xabaras/maven/RecyclerViewSwipeDecorator/_latestVersion) and add it to your project's libraries.

## Usage ##
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
new RecyclerViewSwipeDecorator.Builder(MainActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.my_background))
            .addActionIcon(R.drawable.my_icon)
            .create()
            .decorate();
```
It's suggested to use 24dp square vector drawables.

#### Customizing ####
You can choose different background/icon combinations for left and right swipe directions by using direction specific methods in the Builder object.
It's also available a method to set the action icon margin from the view left/right bound
For any other question about creating a RecyclerViewSwipeDecorator refer to the Java Docs
![javadoc](https://xabaras.github.io/RecyclerViewSwipeDecorator/)
Feel also free to check out the full code of the sample app (inside this repo) for a concrete example of library usage.

##### public Builder addSwipeRightBackgroundColor(int color) #####
Add a background color to the view while swiping right.

##### public Builder addSwipeRightActionIcon(int color) #####
Add an action icon while swiping right (it's suggested to use 24dp square vector drawables.).

##### public Builder addSwipeLeftBackgroundColor(int color) #####
Add a background color to the view while swiping left.

##### public Builder addSwipeLeftActionIcon(int color) #####
Add an action icon while swiping left (it's suggested to use 24dp square vector drawables.).

##### public Builder setIconHorizontalMargin(int color) #####
Set icon horizontal margin from left/right bound of the view (default is 16dp).