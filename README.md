# Northwest Library 
An android application which is used for searching and renting books from a library using android mobile.

<h2>App Name:</h2>
<h3>Northwest Library</h3>

<h2>App Logo<h2>
<p align="center">
  <img src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/appLogo.jpeg" width="250" height="250" />
</p>
  
<h2>Team Members:</h2>
<ul>
 <li> Anil Bomma</li>
 <li> Deepthi Tejaswani Chokka</li>
 <li> Rethimareddy Polam</li>
 <li> Mahender Reddy Surkanti</li>
</ul>


<h2> Introduction to our Project:</h2>
<p>
  Our B. D. Owens Library is maintaining website for the book tracking. To know where a specific book is located in library, we need to   use that website only. By our project we will create an android application in which we will keep track of all the books in the         library. Admin will add the book name, details and the location where it is kept and the users will search the book by book name or     description and can see in which place the book is located. The users/Student can also view the books from the library and also know     the details like due date and extra charges for late return from our appliation.
</p>
  
<h2>Specifications/Support Version:</h2>
<p>
    We are using android API version of pixel 3a XL API 28. The size of the layout is 1080*2160 with a density of 400. The complete details of the device we have used in our project is:
    <li>Device Name: Pixel 3a XL API 28</li>
    <li>Android API: 9.0 API 28</li>
    <li>Size: 1080*2160 </li>
</p>



<h2>Database:</h2>
<p> Firebase </p>
<p> 
  We are using a Persistent database named Firebase Fire Store. By using the fire Store in our application, we can store the data in cloud and sync it across all the devices when ever required. We are storing information regarding user details, books information and borrowed books details in our database. By using this we structure the data depending on our requirements. We can query and fetch data easily as per requirements. We can also dynamically get the data whenever we require. The database will grow dynamically depending on the information stored. We are also using firebase authentication for validating and logging the user into the application. 
</p>
   
   
   
<h2>APK file</h2>
https://github.com/anil-bomma/android-library-app/blob/master/LibraryApk.apk
    
    
    
<h2>Installation Steps</h2>
<p>
  Installation of this app is much more easier with just few steps. The User can download the apk file of the LibraryApplication App. By  clicking on the apk file, It install's the app in your android mobile. Once the App gets installed the user can click on the App icon to  open it. Later on, the user can use the application as per their requirements.
</p>
  
  
  
<h2>Requirements:</h2>
<p>
  <li>Mobile with the Android operating System.  </li>
  <li>Minimum 1 GB RAM.  </li>
  <li>Minimum 8 GB internal storage  </li>
  <li>To run this app we need internet.  </li>
</p>



<h2>User Credentials for testing: </h2>
<p>
  <h3>Login as Admin:</h3>
  <ul>
    <li><b>UserID:</b> s537157@nwmissouri.edu</li>
    <li><b>Password:</b> 123456</li>
  </ul>
  <h3>Login as Librarian</h3>
  <ul>
    <li><b>UserID:</b> s537240@nwmissouri.edu</li>
    <li><b>Password:</b> 123456</li>
  </ul>
  <h3>Login as Student:</h3>
  <ul>
    <li><b>UserID:</b> s536974@nwmissouri.edu</li>
    <li><b>Password:</b> 123456</li>
  </ul>
</p>


<h2>Sources used for our Project:</h2>
<p>
  For Completion of our project we have used many websites. Few of them are listed below.  
  <li> https://developer.android.com/</li>
  <li> https://stackoverflow.com/ </li>
  <li> https://www.youtube.com/watch?v=fGcMLu1GJEc </li>
  <li> https://www.youtube.com/watch?v=tLVz5wmNyrw</li>
</p>


<h2> Sequential flow and detailed description of our project</h2>
<p>
  Our application <b>Northwest Library</b> allows the users to know the location of book they are searching in B.D Owens Lirary. By using our applictaion the user can find the book they require without wasting their time in searching book manually. They can also know weather the book is available in the library or not using our app. They can borrow a book if required and also view the due date to return the book which they have borrowed earlier.
</p>
<ul>
  <li>
    Once the user open the application even before login, they can view the <b>list of books</b> that are available in our library. They can view detailed <b>description of a book</b> by clicking on a particular book they want.
  </li>
  <li>
    By reading the description the user can know wheather they want that book or not. If they want that book they can view location of it. If they want to <b>borrow that book</b> they can do it there itself.
  </li>
  <li>
    To borrow a book the login to the application is required. If the user has already logged in and clicked on <b>borrow book,</b> the due date to return the book will be shown and an alert box is displayed to <b>confirm the borrow action</b>. Once user confirmed it, the book ID along with used ID will be added to the <b>borrowed books list</b> in database.<br>
    If they have not logged in before, the application will redirect them to <b>login activity</b>.
  </li>
  <li>
    If a user is new to the application, they should <b>register</b> with the personal information and then login using mail id and password. <b>(OR)</b> If already registered but had <b>forgot his/her password,</b> they can request for <b>resending the password</b> and a <b>email</b> will be sent to the user with the old password. Initially every user is added with role as student which can later be updated as Librarian if the admin wants them to be.
  </li>
  <li>
    So according to the user role the view of the application changes. 
    <ul>
      <li>
      If the User role is <b>Student</b>, they can perform actions like <b>viewing the list of books</b> available in the library, <b>look for the location of a particular book</b> in the library, track the list of the <b>books he/she have already borrowed</b> and check their due dates to return.
     </li>
    </ul>
    <ul>
      <li>
         If the User role is <b>Librarian</b>, they can perform all the actions done by the user. A part from that, if new books are bought by libary, those book details and the storage location can be <b>added(book) into database</b> by the him/her. They can also view the <b>list of books borrowed by students</b> along with their corresponding #919 number and due date to for every book.
      </li>
    </ul>
    <ul>
      <li>
         If the User role is <b>Admin</b>, he/she can perform all the functionalities done by student and department admin. A part from that, they can view the <b>statistics of usage of books</b> present in the library.They can aslo make a student as librarian/department admin by <b>adding them to Librarian list</b>. This privilage is given only to admin. Admin can also view the <b>list of librarians</b> present. He/She can <b>remove</b> a person's role from librarian and make them a normal user that is, Student if they want.
      </li>
    </ul>
    </li>
  <li>
    Apart from all the mentioned functionalities, all users who login are provided with some other basic features like view the <b>user profile, change password, help screen and Contact Us</b> page with all the related data in it.
  </li>
</ul>






<h2>Layouts of our project:</h2>
<h3>Splash Screen:</h3>
<p>
  This is the welcome page to our application. The splash screen will be the launch page of our application. This screen acts as a loading page before launching the application. 
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/SplashScreen.png" width="230" height="400"/>
<br>Fig: 01
</p>

<h3>List all books:</h3>
<p>
  This is the main activity of our application. This screen consists of list of all available books in the library. The screen appears as below.
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/listBooks.png" width="230" height="400" />
<br>Fig: 02
</p>

<h3>Book description:</h3>
<p>
  In this page the detailed description of a particular book clicked or selected from the list will be displayed as shown below. If the user clicks on borrow book button and if the user is not logged in, the application will be redirected to login activity. Otherwise corresponding screen is shown in fig: 18
</p>
<p align="center">
<img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_BookDescription.png" width="230" height="400"/>
<br>Fig: 03
</p>

<h3>Basic Navigation bar:</h3>
<p>
  This is the navigation bar to any user who didn't log in. Shown are the list of functionalities a user can do before logging into the application.
</p>
<p align="center">
<img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_BasicNavBar.png" width="230" height="400"/>
<br>Fig: 04
</p>

<h3>Search Book:</h3>
<p>
  In this screen, if we write any thing in the edit text and click on search all the books related to given description will be displayed below the button as shown.By clicking any of the book from the list a screen with detailed description of that book is shown.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_SearchBook.png" width="230" height="400"/>
  <br>Fig: 05
</p>

<h3>Help Screen:</h3>
<p>
  In this screen, any basic information required are shown.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_HelpScreen.png" width="230" height="400"/>
  <br>Fig: 06
</p>

<h3>Contact Us Screen:</h3>
<p>
  In this screen, contact information of developers are provided to getting in touch with them. By clicking on any developers contact block the app will redirect to the local callin application in that mobile.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_ContactUsScreen.png" width="230" height="400"/>
  <br>Fig: 07
</p>

<h3>Login Screen:</h3>
  <p>
    The login page will be used for the app in which the user will enter their credentials like username and password. If they are new user they have link called register, by clicking it they will redirect to registration page where the details of the user/students are entered. If the user forgets their password the corresponding link to this are also provided as shown below.
  </p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_LoginPage.png" width="230" height="400"/>
  <br>Fig: 08
</p>

<h3>Registration Screen:</h3>
<p>
  If the student/user is new to the application they need to register themselves into this by giving the basic details for them like name, #919 number and contact details. We have also add form validations for the inputs given.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_RegisterPage.png" width="230" height="400"/>
  <br>Fig: 09
</p>

<h3>Verification Gmail:</h3>
<p>
  This an example of the mail sent to a user of verification code to register them. 
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_gmailVerificationCode.png" width="800" height="500"/>
  <br>Fig: 10
</p>

<h3>Verification Screen:</h3>
<p>
  In this screen, the verification code sent in the mail is entered.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_verificationScreen.png" width="230" height="400"/>
  <br>Fig: 11
</p>

<h3>Registration Successful mail:</h3>
<p>
  This an example of the mail sent to a user stating the successful registration of the user.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_gmailLoginSuccess.png" width="800" height="500"/>
  <br>Fig: 12
</p>

<h3>Forget password Screen:</h3>
<p>
  When the user clicks on forget password link in login screen they will be directed to this screen. In this screen, the user will enter the mail id which they have provided during registration and clicks on get verification code as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_forgetPassword.png" width="230" height="400"/>
  <br>Fig: 13
</p>

<h3>Forget password gmail:</h3>
<p>
  Once the clicks on get verification code button in the above screen, if the mail Id entered matches mail ID's in database a verification code is sent to that mail ID to verify wheather the right person access the applictaion. The mail appears as follows. 
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_gmailForgetPasswordCode.png" width="900" height="500"/>
  <br>Fig: 14
</p>

<p>
  Once the verification code is sent to the mail. You will be allowed to enter the verification code as shown below. 
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_forgetPasswordVerification.png" width="230" height="400"/>
  <br>Fig: 15
</p>

<h3>Password mail:</h3>
<p>
  Once the verification code entered matches the code sent, a mail with old password is sent to the mail so that the user can know the  password and login to their profile. 
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_gmailPasswordSent.png" width="900" height="500"/>
  <br>Fig: 16
</p>

<h3>Once the user logs in their are three kinds of user who are using this application they are
</h3>
<ul>
  <p>1. Student</p>
  <p>2. Librarian</p>
  <p>3. Admin</p>
</ul>

<h3> Other than the above mentioned screens, the following are the screen that can be accessed by the user with role as <b>Student</b></h3>

<h3>Navigation bar for a Student:</h3>
<p>
  The following is the navigation bar for a student showing all the functionalities they can do using this application.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_StudentNavBar.png" width="230" height="400"/>
  <br>Fig: 17
</p>

<h3>Borrow book:</h3>
<p>
  This screen appears when a user clicks on borrow option which is present in description page of a selected book. This screen shows the borrow date and estimated retured date if the borrow is done. If the borrow is made successful, that book will be stored in database with corresponding user Id and book Name. 
</p>
<p>Note: If the selected book is already borrowed by the user the borrow action is declined</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_BorrowedVerification.png" width="230" height="400"/>
  <br>Fig: 18
</p>

<h3>List of the books borrowed by student:</h3>
<p>
  This screen lists all the books the logged student have borrowed from the library along with its due date to return as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_ListBorrowedBook.png" width="230" height="400"/>
  <br>Fig: 19
</p>

<h3>User Profile:</h3>
<p>
  Apart from the main functionality, the user can also view their profile details they entered during registration as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_UserProfileScreen.png" width="230" height="400"/>
  <br>Fig: 20
</p>

<h3>Change Password Screen:</h3>
<p>
  In this screen, the user can also change the password from which they have entered during registration if they feel to. In this screen the user enters the old password and new password into respective entry fields. If the entered old password and the password coresponding the user matches only then the newly entered password will be updated otherwise an error message is sent to user stating that the old password entered is wrong.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_ChangePasswordScreen.png" width="230" height="400"/>
  <br>Fig: 21
</p>

<h3> Other than the above mentioned functionality for general student users, the following are the screen that can be accessed by the user with role as <b>Librarian</b></h3>

<h3>Navigation bar for a Librarian:</h3>
<p>
  The following is the navigation bar for a Librarian showing all the functionalities they can do using this application.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_DptAdminNavBar.png" width="230" height="400"/>
  <br>Fig: 22
</p>

<h3>Adding books to the database:</h3>
<p>
  If the library management bought new books, the department admin can add them to the database by entering the details of books in this screen.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_AddBook.png" width="230" height="400"/>
  <br>Fig: 23
</p>

<h3>List of the books borrowed by students:</h3>
<p>
  This screen is different from that which appears to a student. In this screen, all the books which are borrowed by different students are listed along with their student ID of the person taken and the due date to return as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_BorrowedBooksList.png" width="230" height="400"/>
  <br>Fig: 24
</p>

<h3> Other than the above mentioned functionality for general student users and Librarian, the following are the screen that can be accessed by the user with role as <b>Admin</b></h3>

<h3>Navigation bar for a Admin:</h3>
<p>
  The following is the navigation bar for a Admin showing all the functionalities they can do using this application.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_AdminNavBar.png" width="230" height="400"/>
  <br>Fig: 25
</p>

<h3>Book statistics:</h3>
<p>
  This screen is only visible to admin. In this screen, admin can view the utilization of the books that are in library. For any book in the library that has been borrowed, the number of times it is borrowed can be seen her. We thought that this statistics will be if the admin wants to know the usage of the books in library.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_bookStats.png" width="230" height="400"/>
  <br>Fig: 26
</p>

<h3>Add a person as Librarian:</h3>
<p>
  In this screen, if the admin wants a student to be employeed as department admin/Librarian, they can find the person by entering their #919 number and find the person as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_AddDprAdmin.png" width="230" height="400"/>
  <br>Fig: 27
</p>

<h3>After finding..</h3>
<p>
  In this screen, the details of student with matching #919 is shown as. And if requied, the admin can update the selected student to promote as Librarian then and their itself by clicking on add button.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_AddedDptAdmin.png" width="230" height="400"/>
  <br>Fig: 28
</p>

<h3>User Profile of a person after updating the role to Librarian:</h3>
<p>
  Once a student's role is updated to Librarian this will be reflected in the user profile screen of that person. Before the role was <b>student</b> which is appearing in fig: now the role is updated to department admin as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_DptUserProfile.png" width="230" height="400"/>
  <br>Fig: 29
</p>

<h3>List all Librarians:</h3>
<p>
  We can see the list of the Librarians who are employeed at present in this screen as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_DptAdminList.png" width="230" height="400"/>
  <br>Fig: 30
</p>

<h3>Remove a Librarians role and make them a student:</h3>
<p>
  From the above screen, we can see a remove button corresponding to each person in the list. If we click on any of the persons remove button, an alert box appears and ask for the conformation of the remove action to be performed as shown below.
</p>
<p align="center">
  <img  src="https://raw.githubusercontent.com/Rethima-Reddy/sample/master/Images/new_deleteDptAdminAlertBox.png" width="230" height="400"/>
  <br>Fig: 31
</p>
<p>
  If the <b>REMOVE</b> is selected then that person is removed from the list immediately and their role will be updated to student in the database immediately.
</p>



<h2>Contributors:</h2>
<ul>
 <li> Anil Bomma</li>
 <li> Rethimareddy Polam</li>
 <li> Mahender Reddy Surkanti</li>
 <li> Deepthi Tejaswani Chokka</li>
</ul>
