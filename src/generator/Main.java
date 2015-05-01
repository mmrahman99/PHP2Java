package generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import translator.Executer;

public class Main {

	public static void main(String[] args) {
		//String inputname = "input2.php";
		//File input = new File(inputname);
		//������ � ���������� - ��� �������� �����
		if (args.length < 1)
			throw new IllegalArgumentException("No arguments error: need a filename");

		File input = new File(args[0]);
		if (!input.exists())
			throw new IllegalArgumentException("File '" + args[0] + "' doesn't exist");
		//�������� .php � �����
		int lastIndex = input.getName().lastIndexOf(".");
		String outputname = (lastIndex == -1)? input.getName(): input.getName().substring(0, lastIndex);
		System.out.println("Generating " + outputname + ".java");
		//  ��������� java-������
		JavaClassGenerator jdc = new JavaClassGenerator(outputname);
		try {
			// ��������� ������� ����� ������ (package, public class Name)
			jdc.generateStart();
			
			Executer e = new Executer();
			e.setFile(input);
			e.execute(); // ����� �����������, � ���������� � ��� ����� 2 ������:
			//1� - ��� � main , 2� - ������ ������� ������
			
			jdc.generateMainBody(e.getCodelist()); //��������� ����
			jdc.generateMethods(e.getFunctionList()); //��������� ������
			
			jdc.generateEnd(); //����������� �������� ������
			
		} catch (FileNotFoundException e) {
			System.err.println("File "+input.getName()+" not found: \n" + input.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("IO error while writing "+input.getName());
		}
		
		
	}
}
