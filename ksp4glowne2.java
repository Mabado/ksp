import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

class ksp4glowne2 extends JFrame implements ActionListener
{
JPanel jp;
JPanel wykr = new JPanel();
JPanel wykr_2 = new JPanel();
JPanel wykr_3 = new JPanel();
JPanel wykr_4 = new JPanel();
JPanel wykr_5 = new JPanel();
JPanel obs = new JPanel();
double [][] dane = new double[6000][3];
double [][] danehist = new double[6000][2];
double [][] dane2 = new double[1024][3];

double [][] energia = new double[1024][3];
int N=0;
JButton b1 = new JButton("Start");
JButton b2 = new JButton("Odswiez");
JButton b3 = new JButton("Reset");


public ksp4glowne2(final String title)
{
super(title);

this.getContentPane().setSize(1260,840);
this.getContentPane().setLayout(null);



wykr.setBounds(10,110,300,300);
wykr.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
wykr.setVisible(true);

wykr_2.setBounds(320,110,300,300);
wykr_2.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
wykr_2.setVisible(true);

wykr_3.setBounds(10,420,300,300);
wykr_3.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
wykr_3.setVisible(true);

wykr_4.setBounds(320,420,300,300);
wykr_4.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
wykr_4.setVisible(true);

wykr_5.setBounds(630,110,600,610);
wykr_5.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
wykr_5.setVisible(true);

obs.setBounds(10,5,600,40);
obs.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
obs.setVisible(true);

this.getContentPane().add(wykr);
this.getContentPane().add(wykr_2);
this.getContentPane().add(wykr_3);
this.getContentPane().add(wykr_4);
this.getContentPane().add(wykr_5);
this.getContentPane().add(obs);

setVisible(false);

obs.add(b1);
b1.setBounds(70,65,90,20);
b1.setVisible(true);
b1.addActionListener(this);

obs.add(b2);
b2.setBounds(70,85,90,20);
b2.setVisible(true);
b2.addActionListener(this);

obs.add(b3);
b3.setBounds(70,105,90,20);
b3.setVisible(true);
b3.addActionListener(this);


setVisible(true);


}



public void actionPerformed(ActionEvent arg0) {
if(arg0.getActionCommand() == "Start")
{
	System.out.println(N);
try{
Socket echoSocket = null;
PrintWriter out = null;
BufferedReader in = null;

try
{
echoSocket = new Socket("localhost", 6666);
out = new PrintWriter(echoSocket.getOutputStream(), true);
in = new BufferedReader(new
InputStreamReader(echoSocket.getInputStream()));
String inputLine, outputLine, userInput;
}

catch (UnknownHostException e)
{
System.err.println("Nieznany host: localhost.");
System.exit(1);
}

catch (IOException e)
{
System.err.println("Couldn't get I/O for " + "the connection to: localhost.");
System.exit(1);
}

BufferedReader stdIn = new BufferedReader(new
InputStreamReader(echoSocket.getInputStream()/*System.in*/));

char []d= new char [1024];
int zz=stdIn.read(d);
d[zz]=0;
System.out.println(new String(d));
//get1dhist od 0 getresults od 0

String fromUser = "getresults od 0";

System.out.println("Client: " + fromUser);
out.println(fromUser);


String userInput;
int i=0;
while ((userInput = stdIn.readLine()) != null)
// while ((userInput = stdIn.) != null)
{

if(userInput.compareTo("EOF")==0) break;
StringTokenizer st = new StringTokenizer(userInput);
for(int j=0;j<=2;j++)
{
String dana = st.nextToken();
dane[i][j]=Double.parseDouble(dana);
System.out.print(dana+" ");
if(dane[i][j]>1023 && j>0)continue;
if (j==0)continue;
danehist[Integer.parseInt(dana)][j-1]++;

}
i++;
System.out.println();
}




System.out.println(i);
N=i;
System.out.println(N);


out.close();
in.close();
stdIn.close();
echoSocket.close();
}

catch(Exception ee){
ee.printStackTrace();

}

}

if(arg0.getActionCommand() == "Odswiez")
{
// wykres Tor1
XYSeries series = new XYSeries("Data");
int i;
double v=0;
for (i=0;i<1023;i++)
{

v=danehist[i][0];
System.out.println(v);
series.add(i,v);
}

XYSeriesCollection dataset = new XYSeriesCollection(series);
final JFreeChart chart = ChartFactory.createXYBarChart(
"Tor 1",
"Kanal 1",
false,
"Zliczenia",
dataset,
PlotOrientation.VERTICAL,
true,
true,
false
);

ChartPanel chartPanel = new ChartPanel(chart);
chartPanel.setPreferredSize(new java.awt.Dimension(320, 300));
wykr.add(chartPanel);

// wykres Tor2
XYSeries series_2 = new XYSeries("Data");
int ia;
double vv=0;
for (ia=0;ia<1023;ia++)
{

vv=danehist[ia][1];
System.out.println(vv);
series_2.add(ia,vv);
}

XYSeriesCollection dataset_2 = new XYSeriesCollection(series_2);
final JFreeChart chart_2 = ChartFactory.createXYBarChart(
"Tor 2",
"Kanal 2",
false,
"Zliczenia",
dataset_2,
PlotOrientation.VERTICAL,
true,
true,
false
);

ChartPanel chartPanel_2 = new ChartPanel(chart_2);
chartPanel_2.setPreferredSize(new java.awt.Dimension(320, 300));


wykr_2.add(chartPanel_2);


// wykres energia1
XYSeries series_3 = new XYSeries("Data");

XYSeriesCollection dataset_3 = new XYSeriesCollection(series_3);
final JFreeChart chart_3 = ChartFactory.createXYBarChart(
"Energia 1",
"Energia [keV]",
false,
" ",
dataset_3,
PlotOrientation.VERTICAL,
true,
true,
false
);

ChartPanel chartPanel_3 = new ChartPanel(chart_3);
chartPanel_3.setPreferredSize(new java.awt.Dimension(320, 300));


wykr_3.add(chartPanel_3);

// wykres energia2
XYSeries series_4 = new XYSeries("Data");

XYSeriesCollection dataset_4 = new XYSeriesCollection(series_4);
final JFreeChart chart_4 = ChartFactory.createXYBarChart(
"Energia 2",
"Energia [keV]",
false,
" ",
dataset_4,
PlotOrientation.VERTICAL,
true,
true,
false
);

ChartPanel chartPanel_4 = new ChartPanel(chart_4);
chartPanel_4.setPreferredSize(new java.awt.Dimension(320, 300));


wykr_4.add(chartPanel_4);


this.setVisible(true);

}
if(arg0.getActionCommand() == "Odswiez")
{
	
}

}

public static void main(String[] args)
{

ksp4glowne2 demo = new ksp4glowne2("Okno glowne");


}
}