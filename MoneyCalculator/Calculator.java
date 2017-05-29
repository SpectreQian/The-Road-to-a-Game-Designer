import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.text.NumberFormat;

public class Calculator extends JFrame {
	JButton button1;
	JLabel label1, label2, label3, label4;
	JTextField textField1, textField2;
	JCheckBox dollarSign, commaSeparator;
	JRadioButton addNums, subtractNums, multNums, divideNums;
	JSlider howManyTimes;
	JComboBox target;
	
	
	double number1, number2, totalCalc;
	
	public static void main(String[] args){
		new Calculator();
	}
	
	public Calculator(){
		this.setSize(600, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Money Calculator");
		// create panel
		JPanel Panel = new JPanel();
		// create button
		button1 = new JButton("Calculate");
		ListenForButton lForButton = new ListenForButton();
		button1.addActionListener(lForButton);
		Panel.add(button1);
		
		// create label with following text Field
		label1 = new JLabel("Number 1");
		Panel.add(label1);
		textField1 = new JTextField("",5);
		Panel.add(textField1);
		
		label2 = new JLabel("Number 2");
		Panel.add(label2);
		textField2 = new JTextField("",5);
		Panel.add(textField2);
		
		// create checkbox
		dollarSign = new JCheckBox("Dollars");
		commaSeparator = new JCheckBox("Commas");
		Panel.add(dollarSign);
		Panel.add(commaSeparator, true);
		
		// create RadioButton
		addNums = new JRadioButton("Add");
		subtractNums = new JRadioButton("Subtract");
		multNums = new JRadioButton("Multiply");
		divideNums = new JRadioButton("Divide");
		
		// Group these RadioButton
		ButtonGroup operation = new ButtonGroup();
		operation.add(addNums);
		operation.add(subtractNums);
		operation.add(multNums);
		operation.add(divideNums);
		// create new panel to hold these button
		JPanel operPanel = new JPanel();
		// create border for new panel
		Border operBorder = BorderFactory.createTitledBorder("Operation");
		operPanel.setBorder(operBorder);
		// add buttons to the panel
		operPanel.add(addNums);
		operPanel.add(subtractNums);
		operPanel.add(multNums);
		operPanel.add(divideNums);
		// set default
		addNums.setSelected(true);
		// add new panel to main panel
		Panel.add(operPanel);
		
		// add new label with slider
		label3 = new JLabel("Perform HowManyTimes?");
		Panel.add(label3);
		
		howManyTimes = new JSlider(0, 99, 1);
		howManyTimes.setMinorTickSpacing(1);
		howManyTimes.setMajorTickSpacing(10);
		howManyTimes.setPaintTicks(true);
		howManyTimes.setPaintLabels(true);
		
		ListenForSlider lForSlider = new ListenForSlider();
		howManyTimes.addChangeListener(lForSlider);
		Panel.add(howManyTimes);
		
		// create target label and combo box
		label4 = new JLabel("Select your target vaule: ");
		Panel.add(label4);
		String[] targets = {"-100","-10","1","10","100","1000","10000","100000","1000000"};
		target = new JComboBox(targets);
		target.setMaximumRowCount(4);
		target.setEditable(true);
		Panel.add(target);
		
		// Make MyFrame Visible
		this.add(Panel);
		this.setVisible(true);
		textField1.requestFocus();
		
		
	}
	
	private class ListenForButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1){
				try {
					number1 = Double.parseDouble(textField1.getText());
					number2 = Double.parseDouble(textField2.getText());
				} catch (NumberFormatException exception){
					JOptionPane.showMessageDialog(Calculator.this, "Please enter numbers!", "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				
				if (addNums.isSelected()) {
					totalCalc = addNumber(number1, number2, howManyTimes.getValue());
				} else if (subtractNums.isSelected()) {
					totalCalc = subtractNumber(number1, number2, howManyTimes.getValue());
				} else if (multNums.isSelected()) {
					totalCalc = multiplyNumber(number1, number2, howManyTimes.getValue());
				} else {
					try {
						totalCalc = divideNumber(number1, number2, howManyTimes.getValue());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(Calculator.this, "Do not divide by ZERO!", "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
				
				if (dollarSign.isSelected()){
					NumberFormat numFormat = NumberFormat.getCurrencyInstance();
					JOptionPane.showMessageDialog(Calculator.this, numFormat.format(totalCalc), "Solution", JOptionPane.INFORMATION_MESSAGE);
				} else if (commaSeparator.isSelected()){
					NumberFormat numFormat = NumberFormat.getNumberInstance();
					JOptionPane.showMessageDialog(Calculator.this, numFormat.format(totalCalc), "Solution", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(Calculator.this, totalCalc, "Solution", JOptionPane.INFORMATION_MESSAGE);
				}
				if (addNums.isSelected() || multNums.isSelected()){
					if (totalCalc >= Integer.valueOf((String) target.getSelectedItem())){
						JOptionPane.showMessageDialog(Calculator.this, "Your target is achieved!", "Congrats!", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(Calculator.this, "Your target is not achieved!", "Sorry!", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					if (totalCalc <= Integer.valueOf((String) target.getSelectedItem())){
						JOptionPane.showMessageDialog(Calculator.this, "Your target is achieved!", "Congrats!", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(Calculator.this, "Your target is not achieved!", "Sorry!", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
			
		}
	}
	private class ListenForSlider implements ChangeListener{
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == howManyTimes){
				label3.setText("Perform How Many Times: " + howManyTimes.getValue());
			}
		}
		
	}
	private static double addNumber(double number1, double number2, int howMany){
		
		double total = 0;
		for (int i = 0; i < howMany; i++){
			total = total + (number1 + number2);
		}
		
		return total;
	}
	private static double subtractNumber(double number1, double number2, int howMany){
		
		double total = 0;
		for (int i = 0; i < howMany; i++){
			total = total + (number1 - number2);
		}
		
		return total;
	}
	private static double multiplyNumber(double number1, double number2, int howMany){
		
		double total = 0;
		for (int i = 0; i < howMany; i++){
			total = total + (number1 * number2);
		}
		
		return total;
	}
	private static double divideNumber(double number1, double number2, int howMany) throws Exception{
		if (number2 == 0){
			throw new Exception("DivideByZero");
		}
		double total = 0;
		for (int i = 0; i < howMany; i++){
			total = total + (number1 / number2);
		}
		
		return total;
	}
	
}
