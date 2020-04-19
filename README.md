# android-library-app
Developing a library application which is used for searching and renting books using mobile application.

<h1>App Name:Northwest Library</h1>
<h2>App Logo<h2>
  
<h2>Team Members:</h2>
 <li> Anil Bomma</li>
 <li> Deepthi Tejaswani Chokka</li>
 <li> Rethimareddy Polam</li>
 <li> Mahender Reddy Surkanti</li>


<h2> Introduction to our Project:</h2>
<p>
  Our B. D. Owens Library is maintaining website for the book tracking. To know where a specific book is located in library, we need to go to library and use only library desktops. By our project we will create an android application in which we will keep track of all the books in the library. Admin will add the book name, details and the location where it is kept and the users will search the book by book name or description and can see in which place the book is located. The users/Student can also view the books they already borrowed and the due dates of them are also shown.
  </p>
  
  <h2>Specifications/Support Version:</h2>
<p>We are using android API version of pixel 3a XL API 28. The size of the layout is 1080*2160 with a density of 400. 
The complete details of the device we have used in our project is:
Device Name: Pixel 3a XL API 28
Android API: 9.0 API 28
Size: 1080*2160 </p>
Database: Firebase
<h2>APK file</h2>
https://github.com/anil-bomma/android-library-app/blob/master/LibraryApk.apk
    
  <h2>Installation Steps</h2>
  <p>Installation of this app is much more easier with just few steps The User can download the apk file of the LibraryApplication App. By clicking on the apk file, It install's the app in your android mobile. Once the App gets installed the user can click on the App icon to open it. Later on, the user can use the application as per their requirements.
</p>

  
  <h2>Requirements:</h2>
  <p>
  <ul>
Mobile with the Android operating System.
We need the above API properties to run the app and to have good user interface.
Minimum 1 GB RAM.
Minimum 8 GB internal storage
To run this app we need internet.
</ul>
</p>

<h2>Sources used for our Project:</h2>

<p>
  <li> https://stackoverflow.com/questions/15748558/android-developer-documentation-download</li>
  <li> https://www.youtube.com/watch?v=tLVz5wmNyrw</li>
</p>

<h2>Test Credentials:</h2>
<p>
<li>
 Username for Admin: s537157@nwmissouri.edu
 password:123456
 </li>
 <li>
 Username for Department Admin: s537240@nwmissouri.edu
 password:123456
 </li>
</p>

<h2>Layouts of our project:</h2>
   <ul>
 <h3>Splash Screeen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/SplashScreen.png" />
  <p>
The splash screen will be the launch page. This screen acts as a splash screen. A splash screen is usually used when a game or program is launching. In this app, a splash screen is used to launch the app and show it like a welcome screen.   
  </p>
     
  </ul>
 <ul>
   <h3>Login Screen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/LoginScreen.png" />
  <p>
The login page will be used for the app in which the user will enter their credentials like username and password of if they are new user they have button called register, by clicking it they will redirect to registration page where the details of the user/students are entered. 
  </p>
  </ul>
  <ul>
   <h3>Registration Screen :</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/RegisterScreen.png" />
  <p>
If the student/user is new to the application they need to register themselves into this by giving the basic details for them like name, #919 number and contact details. We will also add form validations for the inputs given.
  </p>
  </ul>
  <ul>
   <h3>Home Screen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/ListAllBooks.png" />
  <p>
The user will enter into home page of the application. This screen will display the list of books available in our library app. By clicking upon that particular book, you will be re-directed to "About the Book" Screen.  
  </p>
  </ul>
    <ul>
   <h3>About the Book Screen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/AboutTheBook.png" />
  <p>
This screen is useful to know the in-depth information of that particular book. If the user is interested in borrowing the book, the user can click upon the BORROW BOOK Button. The borrow book button allocates the user to borrow a book as and when required. 
  </p>
  </ul>
   <ul>
   <h3>Navigation Screen:</h3>
  <p>
    The functionalities present in this app can be displayed in the navigation screen as displayed below. This navigation screen will be a basic screen which is displayed to the user of the book app.
  </p>
  
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/InitialFragment.PNG" />
  
   <p>
The screen displayed below is the navigation screen which is to be available for the department admin. We are making sure that all of the functionalities which are available are available to only certain people.
  </p>
  
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/LoginAsAdmin.PNG" />
 
  </ul>
  <ul>
   <h3>Book Search Screen : </h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/SplashScreen.png" />
  <p>
This screen will show up search option will be displayed on top of screen and the sections under it. Once the book needed is selected the section and the row in which the book is present is highlighted. 
  </p>
  </ul>
  <ul>
   <h3>Profile Screen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/UserProfile.PNG" />
  <p>
This page will show the details of the user/student which they have entered at the time of registration.
  </p>
  </ul>
  <ul>
   <h3>Adding Books Screen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/AddingABook.PNG" />
  <p>
This privilege is only given to admins. Every time a new book imported, the book details are entered by the admin. This page will contain all the edit text to add details of the book. The details will be stored in database.
  </p>
  </ul>
  
   <ul>
   <h3>Change Password Screen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/ChangePassword.PNG" />
  <p>
    If the person who has logged in desires to change the password, then the concerned person can do so in this page.
  </p>
  </ul>
  
   <ul>
   <h3>Contact-Us Screen:</h3>
  <img src="https://raw.githubusercontent.com/anil-bomma/android-library-app/master/Images/CotactUsScreen.png" />
  <p>
   The contact-us screen is used to display the details to contack in case of occurence of any queries. They can reach out to a person by through email or by calling the concerned person. An individual can re-direct to the concerned option required by clicking upon the displayed buttons 
  </p>
  </ul>
  

 

