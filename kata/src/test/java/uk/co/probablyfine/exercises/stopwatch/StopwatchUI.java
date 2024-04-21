package uk.co.probablyfine.exercises.stopwatch;

import java.awt.*;
import javax.swing.*;
import uk.co.probablyfine.exercises.stopwatch.StopwatchTest.Stopwatch;
import uk.co.probablyfine.exercises.stopwatch.StopwatchTest.TimeProvider;

public class StopwatchUI {

    public static void main(String... args) {
        new StopwatchUI().start();
    }

    public void start() {
        var stopwatch = createRealStopwatch();

        JFrame stopwatchUI = new JFrame("Stopwatch");

        stopwatchUI.getContentPane().setLayout(new GridBagLayout());

        createDisplay(stopwatch, stopwatchUI);

        addButton(stopwatchUI.getContentPane(), "Start", stopwatch::start, 1, 1);
        addButton(stopwatchUI.getContentPane(), "Stop", stopwatch::stop, 1, 2);
        addButton(stopwatchUI.getContentPane(), "Lap", stopwatch::lap, 2, 1);
        addButton(stopwatchUI.getContentPane(), "Reset", stopwatch::reset, 2, 2);

        stopwatchUI.pack();
        stopwatchUI.setLocationRelativeTo(null);
        stopwatchUI.setVisible(true);
    }

    private static void createDisplay(Stopwatch stopwatch, Container ui) {
        var area = new JTextPane();
        area.setEditable(false);

        new Timer(100, (e) -> area.setText(stopwatch.display())).start();

        ui.add(
                area,
                new GridBagConstraints() {
                    {
                        fill = GridBagConstraints.HORIZONTAL;
                        ipady = 100;
                        weightx = 0.0;
                        gridwidth = 3;
                        gridx = 0;
                        gridy = 0;
                    }
                });
    }

    public static void addButton(Container container, String label, Runnable action, int x, int y) {
        var button = new JButton(label);
        button.addActionListener((e) -> action.run());

        container.add(
                button,
                new GridBagConstraints() {
                    {
                        fill = GridBagConstraints.HORIZONTAL;
                        weightx = 0.0;
                        gridx = x;
                        gridy = y;
                    }
                });
    }

    private Stopwatch createRealStopwatch() {
        var systemClock =
                new TimeProvider() {
                    @Override
                    public long time() {
                        return System.currentTimeMillis() / 1000;
                    }
                };

        return new Stopwatch(systemClock);
    }
}
