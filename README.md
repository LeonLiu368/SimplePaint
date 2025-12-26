# SimplePaint (Java AWT/Swing)

Paint app with Microsoft Paint inspired features built with Java AWT/Swing.

## Run (no build tool)
From the project folder:

```bash
javac -d out src/SimplePaint.java
java -cp out SimplePaint
```

## Controls
- Click the color squares (left) to choose a color (including Custom RGB).
- Click tool boxes to select Pencil/Brush/Roller/Spray/Eraser/Clear.
- Line/Circle/Oval/Polygon/Star prompt for settings and draw on drag.

## Notes
- This uses a `BufferedImage` as a canvas and repaints it onto a Swing `JComponent`.
