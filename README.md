# TrackMD: Hospital Management System

TrackMD is a JavaFX desktop app with separate portals for **patients**, **nurses** and **doctors**:

- **Patients** sign up, log in, see vitals and notes from their latest visit, update their contact info, and message their care team.
- **Nurses** register new patients, look up existing ones, and record a visit (vitals, allergies, history, immunizations, concerns).
- **Doctors** look up a patient, review the latest visit, prescribe medication, and message the patient.

Data is stored as plain `.txt` files in the folder the app runs from. Messages are kept in memory only, so they are lost when the app closes.

## Requirements

- JDK 21 or newer
- Maven 3.8 or newer (it downloads JavaFX 21 automatically)

## Run it

```bash
git clone <repo-url> TrackMD
cd TrackMD

# optional: load the demo accounts and patient
cp sample-data/*.txt .

mvn javafx:run
```

Run the app from the project root, because that's where it reads and writes its data files.

**Demo logins** (after copying `sample-data/`). Pick the matching role on the login screen:

| Role    | Username | Password   |
|---------|----------|------------|
| Doctor  | `Doctor` | `Password` |
| Nurse   | `Nurse`  | `Password` |
| Patient | `pat`    | `pat`      |

In the doctor or nurse portal, search for patient **pat pat**, born **2024-04-01**.

**Eclipse:** use *File → Import → Existing Maven Project* and run `application.Main`.

## Project layout

```
TrackMD/
├── pom.xml                 Maven build (JavaFX deps, `mvn javafx:run`)
├── sample-data/            Demo data files; copy to the project root to use them
└── src/
    ├── application/        App entry point, navigation and file storage
    ├── Controllers/        One controller per FXML screen
    ├── Models/             Plain data classes
    ├── Services/           Messaging
    ├── Views/              FXML screens (the current UI)
    ├── fxml/               Old FXML screens (not used)
    └── resources/logo.png  Logo shown in the screens
```

## What each file does

### `src/application/`
| File | Purpose |
|------|---------|
| `Main.java` | Entry point. Opens `Views/Login.fxml` and applies `application.css`. |
| `ViewFactory.java` | Singleton that switches screens: it closes the current window and opens the requested FXML view. |
| `LoginFile.java` | Account storage. Reads and writes `<username>.txt` (username, password, role, first name, last name, date of birth). |
| `PatientFile.java` | Patient contact record. Reads and writes `<first><last><dob>.txt` (name, date of birth, phone, email). |
| `PatientVisitFile.java` | Visit records. Writes `<first><last><dob>visitN.txt` files numbered upward, reads the latest one, and rewrites it when a doctor prescribes. |
| `application.css` | Global stylesheet (currently empty). |
| `Login.java`, `CreateAccount.java`, `User.java`, `Test.java` | Old prototype classes that only `src/fxml/` uses. The running app doesn't use them. |

### `src/Controllers/`
| File | Screen | Purpose |
|------|--------|---------|
| `LoginController.java` | `Login.fxml` | Checks the username, password and role against `LoginFile`, then sends the user to their portal. |
| `PatientSignUpController.java` | `PatientSignUp.fxml` | Validates the form, creates the login and patient files, and opens the patient portal. |
| `PatientPortalController.java` | `PatientPortal.fxml` | Shows the patient's latest vitals and notes, saves contact edits, and sends and shows messages. |
| `NurseEntrancePortalController.java` | `NurseEntrancePortal.fxml` | Nurse home screen: choose a new patient or an existing one. |
| `RegisterPatientController.java` | `RegisterPatient.fxml` | Creates a new patient record (name and date of birth). |
| `NursePortalController.java` | `NursePortal.fxml` | Finds a patient, records a new visit, and opens messaging. |
| `DoctorPortalController.java` | `DoctorPortal.fxml` | Finds a patient, shows the latest visit, saves a new prescription, and opens messaging. |
| `MessagePopupController.java` | `MessagePopup.fxml` | Chat popup for doctors and nurses, grouped into one conversation per patient first name. |
| `MessagePortalController.java` | `MessagePortal.fxml` | Unfinished screen with fields only and no logic. |

### `src/Models/` and `src/Services/`
| File | Purpose |
|------|---------|
| `Models/Message.java` | One message: sender, conversation ID, text, timestamp. |
| `Models/User.java` | User data class (username or email, password, role). |
| `Models/DataModel.java` | Placeholder for shared app state (not used yet). |
| `Models/users.txt` | Example user list (not read by the app). |
| `Services/MessagingService.java` | In-memory singleton that stores conversations and messages for the session. |

### `sample-data/`
| File | Purpose |
|------|---------|
| `Doctor.txt`, `Nurse.txt`, `Patient.txt`, `pat.txt` | Login accounts. |
| `patpat2024-04-01.txt` | Contact record for patient *pat pat*. |
| `patpat2024-04-01visit1-3.txt` | Three visit records for that patient. The highest number is the latest visit. |

## Known limitations

- Passwords are stored in plain text, and every record is a local text file. There is no database.
- Messages are not saved, so they disappear when the app exits.
- Patients are identified by first name, last name and date of birth, so two patients with the same values would share a record.
