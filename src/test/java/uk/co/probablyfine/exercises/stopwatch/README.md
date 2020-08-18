# stopwatch exercise

The task is to implement a stopwatch with five methods:

* `start()` - begin the timer
* `stop()` - pause the timer
* `lap()` - take a lap measurement
* `reset()` - clear all data

You should also implement `display()` to show the display on the stopwatch.

The display should be in the format:

```
Current Time: mm:ss
Lap 1: mm:ss
Lap 2: mm:ss
...
Lap n: mm:ss
```

## Examples

### Default state

Display:

```
Current Time: 00:00
```

### Taking Laps
Input
```
start()
// wait one second
lap()
// wait by two seconds
lap()
```

Output:

```
Current Time: 00:03
Lap 1: 00:01
Lap 2: 00:02
```