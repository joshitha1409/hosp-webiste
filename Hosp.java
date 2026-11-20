import java.util.List;

//===== Sealed Interfaces for Core Services =====

sealed interface Service permits Appointment, Billing, Report, EHR, Inventory {}
sealed interface User permits Patient, Staff {}

//===== User Types =====

record Patient(String id, String name, int age, String ailment) implements User {}
record Staff(String id, String name, String role) implements User {}

//===== Service Types =====

record Appointment(String patientId, String doctorName, String date) implements Service {}
record Billing(String patientId, double amount, String status) implements Service {}
record Report(String patientId, String details) implements Service {}

//===== New Feature: Electronic Health Records (EHR) =====

record EHR(String patientId, List<String> medicalHistory, List<String> prescriptions) implements Service {}

//===== New Feature: Pharmacy Inventory =====

record Inventory(String itemName, int quantity, String category) implements Service {}

public class Hosp {

     public static void main(String[] args) {
          
         // Sample Users
         List<User> users = List.of(
                 new Patient("P001", "Albert", 30, "Fever"),
                 new Patient("P002", "Mary", 45, "Fracture"),
                 new Staff("S001", "Dr. Smith", "Physician"),
                 new Staff("S002", "Nina", "Nurse")
         );

         // Sample Services Including EHR and Inventory
         List<Service> services = List.of(
                 new Appointment("P001", "Dr. Smith", "2025-04-08"),
                 new Billing("P002", 900.0, "Pending"),
                 new Report("P002", "Healing as expected"),
                 new EHR("P001", List.of("Diabetes", "Allergy to penicillin"), List.of("Paracetamol", "Vitamin D")),
                 new Inventory("Paracetamol", 150, "Medicine"),
                 new Inventory("Syringe", 300, "Equipment")
         );

         // Display Users
         System.out.println("=== User Info ===");
         users.forEach(Hosp::processUser);

         // Display Services
         System.out.println("\n=== Service Info ===");
         services.forEach(Hosp::processService);
    }    

    // ===== Pattern Matching Switch for Users =====
    static void processUser(User user) {
        switch (user) {
            case Patient p -> System.out.println(String.format(
                    "Patient[ID: %s, Name: %s, Age: %d, Ailment: %s]",
                    p.id(), p.name(), p.age(), p.ailment()));
            case Staff s -> System.out.println(String.format(
                    "Staff[ID: %s, Name: %s, Role: %s]",
                    s.id(), s.name(), s.role()));
        }
    }

    // ===== Pattern Matching Switch for All Services =====
    static void processService(Service service) {
        switch (service) {
            case Appointment a -> System.out.println(String.format(  
                     "Appointment[Patient ID: %s, Doctor: %s, Date: %s]",
                     a.patientId(), a.doctorName(), a.date()));
            case Billing b -> System.out.println(String.format(
                     "Billing[Patient ID: %s, Amount: $%.2f, Status: %s]",
                     b.patientId(), b.amount(), b.status()));
            case Report r -> System.out.println(String.format(
                     "Report[Patient ID: %s, Details: %s]",
                     r.patientId(), r.details()));
            case EHR ehr -> {
                     String history = String.join(", ", ehr.medicalHistory());
                     String prescriptions = String.join(", ", ehr.prescriptions());
                     System.out.println(String.format(
                     "EHR[Patient ID: %s, History: [%s], Prescriptions: [%s]]",
                     ehr.patientId(), history, prescriptions));
            }
            case Inventory inv -> System.out.println(String.format(
                     "Inventory[Item: %s, Quantity: %d, Category: %s]",
                     inv.itemName(), inv.quantity(), inv.category()));
         }
     }
}
