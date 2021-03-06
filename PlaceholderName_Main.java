import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;
import javax.swing.*;



class PlaceholderName_Main {
    private static String xml_file_name = "saved_data.xml";
    private static List<course> course_list = new ArrayList<>();
    public static Document xml_doc = null;

    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // initial setup
        // --------------------------------------------------------------------
        File xml_file = new File(xml_file_name);
        if (!xml_file.exists()) {
            generate_empty_xml();
        }

        load_from_xml(course_list);
        // --------------------------------------------------------------------


        JFrame main_gui = new gui();
        swing_console.run(main_gui);

        // gui.refresh();
        // end_process();
    }

    public static void end_process() {
        System.out.println("saving");
        save_to_xml( course_list);
        System.exit(0);
    }

    private static void write_to_xml() {
        File xml_file = new File(xml_file_name);

        XMLOutputter xml_out = new XMLOutputter();
        xml_out.setFormat(Format.getPrettyFormat());
        try {
            xml_out.output(xml_doc, new FileOutputStream(xml_file));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void save_to_xml(List<course> course_list) {
        File xml_file = new File(xml_file_name);

        xml_file.delete();

        generate_empty_xml();

        // courses
        Element course_root = xml_doc.getRootElement().getChild("courses");
        for (int i = 0; i < course_list.size(); i++) {
            course_root.addContent(course_list.get(i).get_xml());
        }

        // TODO: add saving for other objects

        write_to_xml();
    }

    private static void load_from_xml(List<course> course_list) {
        File xml_file = new File(xml_file_name);
        SAXBuilder sax_builder = new SAXBuilder();

        try {
            xml_doc = sax_builder.build(xml_file);

            List<Element> xml_course_list = xml_doc.getRootElement().getChild("courses").getChildren();

            for (int i = 0; i < xml_course_list.size(); i++) {
                course_list.add(xml_to_course(xml_course_list.get(i)));
            }

            // TODO: add loading for other objects
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private static void generate_empty_xml() {
        Element xml_root = new Element("PlaceholderName");
        xml_doc = new Document(xml_root);

        Element courses = new Element("courses");
        xml_doc.getRootElement().addContent(courses);

        Element bills = new Element("bills");
        xml_doc.getRootElement().addContent(bills);

        // TODO: add generation for other objects

        write_to_xml();
    }

    private static course xml_to_course(Element course_element) {
        String temp_name = course_element.getChild("name").getText();
        String temp_department = course_element.getChild("department").getText();
        String temp_number = course_element.getChild("number").getText();
        String temp_section = course_element.getChild("section").getText();
        String temp_start_time = course_element.getChild("start_time").getText();
        String temp_end_time = course_element.getChild("end_time").getText();
        String temp_notes = course_element.getChild("notes").getText();
        boolean temp_monday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("monday").getText());
        boolean temp_tuesday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("tuesday").getText());
        boolean temp_wednesday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("wednesday").getText());
        boolean temp_thursday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("thursday").getText());
        boolean temp_friday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("friday").getText());

        course temp = new course(temp_name, temp_department, temp_number, temp_section, temp_start_time, temp_end_time, temp_monday, temp_tuesday, temp_wednesday, temp_thursday, temp_friday);
        temp.set_notes(temp_notes);

        return temp;
    }

    public static void add_course_to_list(course c) {
        course_list.add(c);
    }

    public static void remove_course_from_list(int index) {
        course_list.remove(index);
    }

    public static int course_list_size() {
        return course_list.size();
    }

    public static boolean is_empty() {
        return course_list.isEmpty();
    }

    public static course get_course_at(int index) {
        return course_list.get(index);
    }
}
