import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.awt.Color;
import java.util.List;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

//TextEditor class starts here
class TextEditor extends Frame implements ActionListener, ItemListener {
    static JTextArea ta = new JTextArea();
    static File f1;
    int i, len1, len, pos1;
    String str = "", s3 = "", s2 = "", s4 = "", s32 = "", s6 = "", s7 = "", s8 = "", s9 = "", strFind = "", strReplace = "";
    String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };

    Integer[] fontSizes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 22, 24, 26, 28, 30, 34, 38, 42, 46, 50 };

    JComboBox fontList = new JComboBox(fontSizes);

    CheckboxMenuItem chkb = new CheckboxMenuItem("Word Wrap");
    Highlighter highlighter;
    HighlightPainter painter;

    // Added Label for About Option
    JLabel lbAbout, lbFontSize;

    JToolBar tlBar;

    Font font;
    Integer fontStyle, fontSize;

    public TextEditor() {
        MenuBar mb = new MenuBar();
        setLayout(new BorderLayout());
        add("Center", ta);
        ta.setBackground(Color.pink);
        fontStyle = Font.ROMAN_BASELINE;
        fontSize = 20;
        font = new Font("Helventica", fontStyle, fontSize);
        ta.setFont(font);

        // Added Vertical and Horizonal Scrollbar
        JScrollPane sp = new JScrollPane(ta);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(sp);

        tlBar = new JToolBar();
        JButton button_FontPlain = new JButton("Plain");
        JButton button_FontBold = new JButton("Bold");
        JButton button_FontItalic = new JButton("Italic");
        button_FontBold.setVisible(true);
        button_FontBold.setBounds(30,50,40,40);
        button_FontItalic.setVisible(true);
        button_FontItalic.setBounds(90,50,40,40);
        lbFontSize = new JLabel("Font Size");
        fontList.setSelectedIndex(17);
        fontList.addActionListener(this);

        tlBar.add(button_FontPlain);
        tlBar.add(button_FontBold);
        tlBar.add(button_FontItalic);
        tlBar.add(lbFontSize);
        tlBar.add(fontList);
        this.add(tlBar, BorderLayout.NORTH);

        button_FontPlain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontStyle = Font.PLAIN;
                font = new Font("Helventica", fontStyle, fontSize);
                ta.setFont(font);
            }
        });
        button_FontBold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontStyle = Font.BOLD;
                font = new Font("Helventica", fontStyle, fontSize);
                ta.setFont(font);
            }
        });

        button_FontItalic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontStyle = Font.ITALIC;
                font = new Font("Helventica", fontStyle, fontSize);
                ta.setFont(font);
            }
        });

        fontList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontSize = (Integer) fontList.getSelectedItem();
                font = new Font("Helventica", fontStyle, fontSize);
                ta.setFont(font);
            }
        });

        setMenuBar(mb);
        Menu m1 = new Menu("File");
        Menu m2 = new Menu("Edit");
        Menu m3 = new Menu("Tools");
        Menu m4 = new Menu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);

        MenuItem mi1[] = {
                new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
                new MenuItem("Page Setup"), new MenuItem("Print"), new MenuItem("Exit")
        };
        MenuItem mi2[] = { new MenuItem("Delete"), new MenuItem("Cut"),
                new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Find"),
                new MenuItem("Find All"), new MenuItem("Replace"),
                new MenuItem("Go To"), new MenuItem("Select All"),
                new MenuItem("Time Stamp") };
        MenuItem mi3[] = { new MenuItem("Choose Font"), new MenuItem("Compile"),
                new MenuItem("Run") };
        MenuItem mi4[] = { new MenuItem("Help Topics"),
                new MenuItem("About TextEditor") };
        for (int i = 0; i < mi1.length; i++) {
            m1.add(mi1[i]);
            mi1[i].addActionListener(this);
        }
        for (int i = 0; i < mi2.length; i++) {
            m2.add(mi2[i]);
            mi2[i].addActionListener(this);
        }
        m3.add(chkb);
        chkb.addItemListener(this);
        for (int i = 0; i < mi3.length; i++) {
            m3.add(mi3[i]);
            mi3[i].addActionListener(this);
        }
        for (int i = 0; i < mi4.length; i++) {
            m4.add(mi4[i]);
            mi4[i].addActionListener(this);
        }

        highlighter = ta.getHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);

        MyWindowsAdapter mw = new MyWindowsAdapter(this);
        addWindowListener(mw);
        setSize(500, 500);
        setTitle("Shaily's notepad");
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String arg = (String) ae.getActionCommand();
        highlighter.removeAllHighlights();
        if (arg.equals("New"))
        {
            dispose();
            TextEditor t11 = new TextEditor();
            t11.setSize(500, 500);
            t11.setVisible(true);
        }
        try
        {
            if (arg.equals("Open"))
            {
                FileDialog fd1 = new FileDialog(this, "Select File", FileDialog.LOAD);
                fd1.setVisible(true);
                String s4 = "";
                s2 = fd1.getFile();
                s3 = fd1.getDirectory();
                s32 = s3 + s2;
                File f = new File(s32);
                FileInputStream fii = new FileInputStream(f);
                len = (int) f.length();
                for (int j = 0; j < len; j++) {
                    char s5 = (char) fii.read();
                    s4 = s4 + s5;
                }
                ta.setText(s4);
                fii.close();
            }
        }
        catch (IOException e)
        {
        }
        try
        {
            if (arg.equals("Save As"))
            {
                FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);
                s7 = dialog1.getDirectory();
                s8 = dialog1.getFile();
                s9 = s7 + s8 + ".txt";
                s6 = ta.getText();
                len1 = s6.length();
                byte buf[] = s6.getBytes();
                f1 = new File(s9);
                FileOutputStream fobj1 = new FileOutputStream(f1);
                for (int k = 0; k < len1; k++)
                {
                    fobj1.write(buf[k]);
                }
                fobj1.close();
            }
            this.setTitle(s8 + " TextEditor File");
        }
        catch (IOException e)
        {
        }
        if (arg.equals("Word Wrap")) {
            try {
                saveFile(f1, ta);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }
        if (arg.equals("Exit")) {
            try {
                saveFile(f1, ta);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }
        if (arg.equals("Cut")) {
            str = ta.getSelectedText();
            i = ta.getText().indexOf(str);
            ta.replaceRange(" ", i, i + str.length());
        }
        if (arg.equals("Copy")) {
            str = ta.getSelectedText();
        }
        if (arg.equals("Paste")) {
            pos1 = ta.getCaretPosition();
            ta.insert(str, pos1);
        }
        if (arg.equals("Delete")) {
            String msg = ta.getSelectedText();
            i = ta.getText().indexOf(msg);
            ta.replaceRange(" ", i, i + msg.length());
            msg = "";
        }
        if (arg.equals("Select All")) {
            String strText = ta.getText();
            int strLen = strText.length();
            ta.select(0, strLen);
        }
        if (arg.equals("Time Stamp")) {
            GregorianCalendar gcalendar = new GregorianCalendar();
            String h = String.valueOf(gcalendar.get(Calendar.HOUR));
            String m = String.valueOf(gcalendar.get(Calendar.MINUTE));
            String s = String.valueOf(gcalendar.get(Calendar.SECOND));
            String date = String.valueOf(gcalendar.get(Calendar.DATE));
            String mon = months[gcalendar.get(Calendar.MONTH)];
            String year = String.valueOf(gcalendar.get(Calendar.YEAR));
            String hms = "Time" + " - " + h + ":" + m + ":" + s + " Date" + " - " + date + " " + mon + " " + year + " ";
            int loc = ta.getCaretPosition();
            ta.insert(hms, loc);
        }
        if (arg.equals("About TextEditor")) {
            AboutDialog d1 = new AboutDialog(this, "About TextEditor");
            d1.add(lbAbout);
            lbAbout.setText("<HTML><p>This is a custom text editor app. The app can be used to replace notepad or similar <br>" +
                    " tool for text editor. <br> <br> I hope you enjoy this cool app !!!</p></html>");
            d1.setVisible(true);
            setSize(500, 500);
        }
        if (arg.equals("Find"))
        {
            strFind = JOptionPane.showInputDialog(ta,"Enter the text to find", null);
            i = ta.getText().indexOf(strFind);

            if (i >= 0)
            {
                try
                {
                    highlighter.addHighlight(i, i + strFind.length(), painter);
                }
                catch (BadLocationException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
        if (arg.equals("Find All"))
        {
            List<Integer> listArray  = new ArrayList<Integer>();
            int index = 0;

            strFind = JOptionPane.showInputDialog(ta,"Enter the text to find all", null);
            i = ta.getText().indexOf(strFind);

            for (String line : ta.getText().split("\\n"))
            {
                String[] tokens = line.split(" ");
                for (int j = 0; j < tokens.length; j++)
                {
                    if (tokens[j].equals(strFind))
                    {
                        listArray.add(index);
                    }
                    index = index + tokens[j].length()+1;
                }
            }

            for (Integer k : listArray)
            {
                if (k >= 0) {
                    try
                    {
                        highlighter.addHighlight(k, k + strFind.length(), painter);
                    }
                    catch (BadLocationException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (arg.equals("Replace"))
        {
            strFind = JOptionPane.showInputDialog(ta,"Enter the text to find", null);
            strReplace = JOptionPane.showInputDialog(ta,"Enter the text to Replace", null);

            i = ta.getText().indexOf(strFind);

            if (i >= 0)
            {
                ta.replaceRange(strReplace, i, i + strFind.length());
            }
        }
        if (arg.equals("Print"))
        {
            try
            {
                boolean complete = ta.print();
                if(complete)
                {
                    JOptionPane.showMessageDialog(null, "Done printing","Information", JOptionPane.INFORMATION_MESSAGE );
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Cancel Printing!","Printer", JOptionPane.ERROR_MESSAGE );
                }
            }
            catch (PrinterException e)
            {
                throw new RuntimeException(e);
            }
        }
        if (arg.equals("Page Setup"))
        {
            SetPageDialog spDialog = new SetPageDialog(this, "Page Setup Dialog");

            JButton button_Portrait = new JButton("Portrait!");
            JButton button_Landscape = new JButton("Landscape!");
            button_Portrait.setVisible(true);
            button_Portrait.setBounds(30,50,40,40);
            button_Landscape.setVisible(true);
            button_Landscape.setBounds(90,50,40,40);
            spDialog.add(button_Portrait);
            spDialog.add(button_Landscape);

            spDialog.setVisible(true);
            spDialog.setSize(250, 100);

            button_Portrait.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    spDialog.dispose();
                    setSize(500, 1000);

                }
            });
            button_Landscape.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    spDialog.dispose();
                    setSize(1000, 500);
                }
            });
        }
        if (arg.equals("Choose Font"))
        {

        }
    }
    public static void main(String args[]) {
        TextEditor to = new TextEditor();
    }

    public void saveFile(File file, JTextArea textArea) throws IOException {
        if (file != null) {
            String filePath = file.getAbsolutePath();
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath)))
            {
                writer.write(textArea.getText());
            } catch (IOException e)
            {
                System.out.println("savefile exception");
            }
        }
        else
        {
            FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
            dialog1.setVisible(true);
            s7 = dialog1.getDirectory();
            s8 = dialog1.getFile();
            s9 = s7 + s8 + ".txt";
            s6 = ta.getText();
            len1 = s6.length();
            byte buf[] = s6.getBytes();
            f1 = new File(s9);
            FileOutputStream fobj1 = new FileOutputStream(f1);
            for (int k = 0; k < len1; k++)
            {
                fobj1.write(buf[k]);
            }
            fobj1.close();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        String arg = (String) e.getItem();
        if (arg.equals("Word Wrap"))
        {
            if (chkb.getState())
            {
                ta.setLineWrap(true);
                ta.setWrapStyleWord(true);
            }
            else
            {
                ta.setLineWrap(false);
                ta.setWrapStyleWord(false);
            }
        }
    }
}

class MyWindowsAdapter extends WindowAdapter {
    TextEditor tt;

    public MyWindowsAdapter(TextEditor ttt)
    {
        tt = ttt;
    }

    public void windowClosing(WindowEvent we)
    {
        try {
            tt.saveFile(TextEditor.f1, TextEditor.ta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tt.dispose();
    }
}

class AboutDialog extends JDialog implements ActionListener {
    AboutDialog(Frame parent, String title) {
        super(parent, title, false);
        this.setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(500, 300);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}

class SetPageDialog extends JDialog implements ActionListener {
    SetPageDialog(Frame parent, String title) {
        super(parent, title, false);
        this.setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}