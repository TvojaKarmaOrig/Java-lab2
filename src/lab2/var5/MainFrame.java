package  lab2.var5;

// Импортируются классы, используемые в приложении
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
// Главный класс приложения, он же класс фрейма
public class MainFrame extends JFrame {

    // Размеры окна приложения в виде констант
    private static final int WIDTH = 650;
    private static final int HEIGHT = 400;

    private int columnCount = 15;

    // Текстовые поля для считывания значений переменных,
    // как компоненты, совместно используемые в различных методах
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;

    private JTextField textVar1;
    private JTextField textVar2;
    private JTextField textVar3;

    private double[] memVariables = {0, 0, 0};
    private double result = 0;

    // Текстовое поле для отображения результата,
    // как компонент, совместно используемый в различных методах
    private JTextField textFieldResult;

    // Группа радио-кнопок для обеспечения уникальности выделения в группе
    private ButtonGroup radioButtons = new ButtonGroup();
    private ButtonGroup memoryButtons = new ButtonGroup();

    // Контейнер для отображения радио-кнопок
    private Box hboxFormulaType = Box.createHorizontalBox();
    private Box vboxMemoryVariables = Box.createVerticalBox();

    private JLabel formulaLabel;

    private int formulaId = 1;
    private int memId = 1;

    // Формула №1 для рассчѐта
    public double calculate1(double x, double y, double z) {
        double part1 = Math.log(Math.pow(1 + x, 2)) + Math.cos(Math.PI * Math.pow(z, 3));
        double part2 = Math.pow(part1, Math.sin(y));
        double part3 = Math.pow(Math.E, Math.pow(x, 2)) + Math.cos(Math.pow(Math.E, z))
                + Math.sqrt(1 / y);
        double part4 = Math.pow(part3, 1 / x);
        return part2 + part4;
    }
    // Формула №2 для рассчѐта
    public double calculate2(double x, double y, double z) {
        double part1 = Math.pow(Math.cos(Math.PI * Math.pow(x, 3)) + Math.pow(Math.log(1 + y), 2), 0.25);
        double part2 = Math.pow(Math.E, Math.pow(z, 2)) + Math.sqrt(1 / x) + Math.cos(Math.pow(Math.E, y));
        return part1 * part2;
    }

    // Вспомогательный метод для добавления кнопок на панель
    private void addRadioButton(String buttonName, final int formulaId) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                MainFrame.this.formulaId = formulaId;
                updateFormulaImage();
            }
        });
        radioButtons.add(button);
        hboxFormulaType.add(button);
    }

    private void addMemButton(String buttonName, final int memId){
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.memId = memId;
            }
        });
        memoryButtons.add(button);
        vboxMemoryVariables.add(button);
    }

    private void updateFormulaImage() {
        try {
            BufferedImage image;
            if (formulaId == 1) {
                image = ImageIO.read(new File("src/lab2/var5/F1.png")); // Путь к изображению формулы 1
            } else {
                image = ImageIO.read(new File("src/lab2/var5/F2.png")); // Путь к изображению формулы 2
            }

            formulaLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            formulaLabel.setText("Ошибка загрузки изображения");
        }
    }

    private void updateVarButton()
    {
        textVar1.setText(Double.toString(memVariables[0]));
        textVar2.setText(Double.toString(memVariables[1]));
        textVar3.setText(Double.toString(memVariables[2]));;
    }

    // Конструктор класса
    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(
                radioButtons.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());
        //hboxFormulaType.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        //vboxMemoryVariables.add(Box.createVerticalGlue());
        addMemButton("Переменная 1", 1);
        addMemButton("Переменная 2", 2);
        addMemButton("Переменная 3", 3);
        memoryButtons.setSelected(memoryButtons.getElements().nextElement().getModel(), true);
        //vboxMemoryVariables.add(Box.createVerticalGlue());
        vboxMemoryVariables.add(Box.createHorizontalGlue());

        Box vboxMemText = Box.createVerticalBox();
        textVar1 = new JTextField("0", columnCount);
        textVar1.setMaximumSize(textVar1.getPreferredSize());
        vboxMemText.add(textVar1);
        vboxMemText.add(Box.createVerticalStrut(3));
        textVar2 = new JTextField("0", columnCount);
        textVar2.setMaximumSize(textVar2.getPreferredSize());
        vboxMemText.add(textVar2);
        vboxMemText.add(Box.createVerticalStrut(3));
        textVar3 = new JTextField("0", columnCount);
        textVar3.setMaximumSize(textVar3.getPreferredSize());
        vboxMemText.add(textVar3);
        updateVarButton();

        Box hboxFormulaImage = Box.createHorizontalBox();
        hboxFormulaImage.add(Box.createHorizontalGlue());
        formulaLabel = new JLabel();
        updateFormulaImage();
        hboxFormulaImage.add(formulaLabel);
        hboxFormulaImage.add(Box.createHorizontalGlue());

        // Создать область с полями ввода для X и Y
        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());
        Box hboxVariables = Box.createHorizontalBox();
        //hboxVariables.setBorder(BorderFactory.createLineBorder(Color.RED));
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());
        // Создать область для вывода результата
        JLabel labelForResult = new JLabel("Результат:");
        textFieldResult = new JTextField("0", columnCount);
        textFieldResult.setMaximumSize(
                textFieldResult.getPreferredSize());
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(labelForResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(textFieldResult);
        hboxResult.add(Box.createHorizontalGlue());
        //hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        // Создать область для кнопок
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    double x = Double.parseDouble(textFieldX.getText());
                    double y = Double.parseDouble(textFieldY.getText());
                    double z = Double.parseDouble(textFieldZ.getText());
                    if (formulaId == 1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    if(Double.isInfinite(result) || Double.isNaN(result))
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Неразрешимая ошибка вычисления при имеющихся данных", "Неверные данные",
                                JOptionPane.WARNING_MESSAGE);
                    else textFieldResult.setText(Double.toString(result));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            }
        });
        JButton buttonMPlus = new JButton("M+");
        buttonMPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memVariables[memId - 1] += result;
                updateVarButton();
            }
        });
        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memVariables[memId - 1] = 0;
                updateVarButton();
            }
        });

        Box hboxButtons = Box.createHorizontalBox();
        //hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonReset);
       // hboxButtons.add(Box.createHorizontalGlue());
       // hboxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        // Связать области воедино в компоновке BoxLayout
        Box contentBox = Box.createVerticalBox();
        Box varBox = Box.createHorizontalBox();
        Box MBox = Box.createHorizontalBox();

        varBox.add(Box.createHorizontalGlue());
        varBox.add(vboxMemText);
        varBox.add(vboxMemoryVariables);
        varBox.add(Box.createHorizontalGlue());

        MBox.add(buttonMPlus);
        MBox.add(Box.createHorizontalStrut(10));
        MBox.add(buttonMC);

        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxFormulaImage);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(varBox);
        contentBox.add(MBox);
        contentBox.add(hboxButtons);
        contentBox.add(Box.createVerticalGlue());

        //memBox.add(MBox);
        //memBox.add(Box.createVerticalGlue());
        //outBox.add(contentBox);
        //outBox.add(memBox);
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    // Главный метод класса
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
