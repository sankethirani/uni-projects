'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/Notifications/{receiver_user_id}/{notification_id}').onCreate((snapshot, context) => {
    const receiver_user_id = context.params.receiver_user_id;
    const notification_id = context.params.notification_id;

    try {
        const user_record = admin.firestore().collection(`users`).doc(receiver_user_id).get();
        const snapval = user_record.data();
        const token = snapval.token;

        return token.then(result => {
            const token_id = result.val();
            const payload =
            {
                notification:
                {
                    title: "New Message",
                    body: `You have just received a new message.`,
                    icon: "default"
                }
            };

            return admin.messaging().sendToDevice(token_id, payload)
                .then(response => {
                    console.log('Notification sent');
                });
        });
    } catch (error) {
        console.log(error);
    }
});


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
