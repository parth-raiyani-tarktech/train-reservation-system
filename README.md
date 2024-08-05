# Train-Reservation-System

You are designing and implementing a system to enable users to book train tickets through an online portal. In other words, you are working for IRCTC to implement the train reservation system.

**Data Input:**

You will be given a list of trains and coach configurations in the below format.

```
2
17726 Rajkot-0 Mumbai-750
17726 S1-72 S2-72 B1-72 A1-48 H1-24
37392 Ahmedabad-0 Surat-300
37392 S1-15 S2-20 S3-50 B1-36 B2-48
```

1. “2” in the first line indicates a total of 2 trains.
2. The first word in each line represents the train number.
3. For each train, the first and last station is mentioned. Along with the station, the distance is mentioned in km.  
   For example, train **17726** travels from Rajkot to Mumbai. **Mumbai is 750 km** away from Rajkot. However, **Rajkot is 0 km** away from itself. This distance will be used while calculating the fare, as explained below.
4. The second line indicates the list of coaches and seats within each coach.  
   For example, **17726 S1-72 S2-72 B1-72 A1-48 H1-24**

This train has 5 coaches, S1, S2, B1, A1 and H1. In S1, S2, and B1, each coach has 72 seats/berths. In A1, there are 48 seats; in H1, there are 24 seats.

The first letter of a coach represents coach type, i.e.

**S - Sleeper (SL)**

**B - 3rd Tier AC (3A)**

**A - 2nd Tier AC (2A)**

**H - 1st class AC (1A)**

**Booking Request:**

Next, the user will enter booking requests, in the following format,

```
Input: Rajkot Mumbai 2024-06-15 SL 6
```

**The user wants to book tickets from Rajkot to Mumbai for the date 15th March 2023. SL indicates Sleeper class. Total passengers: 6**

The following symbols represent different class/coach types (in booking request), i.e. instead

of SL, the user can specify anyone from below.

**SL - Sleeper - 1 INR per KM per passenger**

**3A - 3rd Tier AC - 2 INR per KM per passenger**

**2A - 2nd Tier AC - 3 INR per KM per passenger**

**1A - First Class AC - 4 INR per KM per passenger**

Your program should List down all the available trains for a given route and if number of seats available equals to requested seats in the selected class for a given date.

```
Output: Found 1 trains
Train: 17726
```

Choose the Train:

```
Input: 17726
```

Your program should book the tickets for the selected train

```
Output:
PNR: 1, Train: 17726, Travel Date: 2024-06-15, Total Fare: 4500.00, Booked Seats: S1-1, S1-2, S1-3, S1-4, S1-5, S1-6
```

Assumptions:

1. Each train runs 7 days a week
2. Each train completes the entire journey on the same day, from the first station to the last station
3. Tickets are booked only if seats are confirmed for all passengers.
4. It's acceptable if all tickets are not available in the same coach. For instance, if there are 2 seats available in coach S1 and 3 seats available in coach S2, a booking request for 5 passengers would be accommodated across both coaches.

## Milestone 2: Book Tickets, Retrieve Booking Details using PNR and Generate Report

In this stage, you will enhance the above solution to fulfill the below requirements.

1. User should be able to book the ticket
2. User should be able to retrieve booking details using PNR
3. Generate report

**Example:**

List of trains in below format,

```
3
17726 Rajkot-0 Ahmedabad-200 Vadodara-300 Surat-500 Mumbai-750
17726 S1-72 S2-72 B1-72 A1-48 H1-24
37392 Ahmedabad-0 Anand-50 Vadodara-100 Bharuch-200 Surat-300
37392 S1-15 S2-20 S3-50 B1-36 B2-48
29772 Vadodara-0 Dahod-150 Indore-350
29772 S1-72 S2-72 B1-72 A1-48
```

Note that, for each train, first, last and all intermediate stations will be provided.

For example, train 17726 travels from Rajkot to Mumbai and stops in between at Ahmedabad, Vadodara and Surat.

Ahmedabad-200 represents that the Ahmedabad is 200 KM away from Rajkot

Vadodara-300 represents that the Vadodara is 300 KM away from Rajkot

Surat-500 represents that the Surat is 500 KM away from Rajkot

Mumbai-750 represents that the Mumbai is 750 KM away from Rajkot

**Booking Requests:**
```
Input: Ahmedabad Surat 2023-03-15 SL 3
Output: 17726 37392
```

There are two eligible trains and so display train numbers of both.
```
Next Input: 17726
Output: 100000001 900
```

User has selected 17726 (out of both options) and so the ticket is booked in this train. Total distance is 300 KM and number of passengers are 3, and so the total fair is 900
```
Input: Ahmedabad Surat 2023-03-15 1A 2
Output: 17726
```

**Note:**

1A (First Class AC) is only available in 17726 and so only one train number is displayed. If there are no seats available in any of the eligible trains, then display message “No Seats Available”
```
Next Input: 17726
Output: 100000002 2400
```

**Retrieve booking details using PNR**
```
Input: 100000001
Output: 17726 Ahmedabad Surat 2023-03-15 900 S1-1 S1-2 S1-3
```

For a given PNR 100000001, print complete booking details in above format, i.e.

1. Train Number: 17726
2. From: Ahmedabad
3. To: Surat
4. Date: 2023-03-15
5. Total Fare: 900
6. Seat Details (Coach Number-Seat Number): S1-1 S1-2 S1-3

```
Input: Vadodara Indore 2023-03-15 SL 2
Output: 29772 (Since there is only train for this route)

Next Input: 29772
Output: 100000003 700

Input: 100000025
Output: Invalid PNR

Input: 100000003
Output: 29772 Vadodara Indore 2023-03-15 700 S1-1 S1-2
```

**Generate Report**
```
Input: REPORT

Output:
PNR, DATE, TRAIN, FROM, TO, FARE, SEATS
100000001, 2023-03-15, 17726, Ahmedabad, Surat, 900, S1-1 S1-2 S1-3
100000002, 2023-03-15, 17726, Ahmedabad, Surat, 2400, H1-1 H1-2
100000003, 2023-03-15, 29772, Vadodara, Indore, 700, S1-1 S1-2
```

Rows in report should be sorted by PNR
