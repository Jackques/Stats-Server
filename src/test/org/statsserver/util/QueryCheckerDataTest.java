package org.statsserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class QueryCheckerDataTest {

    public static LinkedHashMap<String, Object> getTHelperMockDataSet(String dummyProjectName) throws JsonProcessingException {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        LinkedHashMap<Integer, Object> resultMap = new LinkedHashMap<Integer, Object>();

        ArrayList<HashMap> rawData = new ObjectMapper()
                .readerFor(Object.class)
                .readValue(fourResultsJson);
        AtomicInteger index = new AtomicInteger();
        rawData.forEach((rawDataItem)->{
            resultMap.put(index.getAndIncrement(), rawDataItem);
        });

        result.put(dummyProjectName, resultMap);
        return result;
    }
    private static final String fourResultsJson = "" +
            "[\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "    },\n" +
            "    \"No\": 1,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"'eigenwijs blondje' schrijf je op je profiel.. Betekent dat jij een slim blondje bent of een dom blondje?\",\n" +
            "        \"datetime\": \"2016-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "        \"datetime\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Wat fijn! Eens een begripvolle vent. Al dat gehaast maar dezer dagen. Dat kan niemand toch meer aan?\",\n" +
            "        \"datetime\": \"2016-02-17T20:07:50.689Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Ik ben slim trouwens. Aangenaam :p\",\n" +
            "        \"datetime\": \"2016-02-17T20:08:12.336Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Ik ben slim trouwens. Aangenaam :p\",\n" +
            "        \"datetime\": \"2016-02-17T20:08:12.336Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2015-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"Daphne123\",\n" +
            "    \"Age\": 30.993107375991197,\n" +
            "    \"City\": \"Roosendaal\",\n" +
            "    \"Job\": \"Logopedist\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": true,\n" +
            "    \"Seems-fake\": false,\n" +
            "    \"Seems-empty\": false,\n" +
            "    \"Seems-obese\": false,\n" +
            "    \"Seems-toppick\": true,\n" +
            "    \"Is-uitblinker-for-Me\": null,\n" +
            "    \"Liked-me-first-is-instant-match\": null,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 3\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Bioscoop\",\n" +
            "      \"Bordspellen\",\n" +
            "      \"Lezen\",\n" +
            "      \"Disney\",\n" +
            "      \"Gamer\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [],\n" +
            "    \"Is-verified\": true,\n" +
            "    \"Amount-of-pictures\": 3,\n" +
            "    \"Attractiveness-score\": 7,\n" +
            "    \"Height\": [\n" +
            "      \"seems-short < 1.60m\",\n" +
            "      \"is-short < 1.60m\",\n" +
            "      \"seems-normal >= 1.60-1.70m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"unclear-or-no-fullbody\",\n" +
            "      \"seems-has-humor\",\n" +
            "      \"has-humor\",\n" +
            "      \"seems-has-MY-humor\",\n" +
            "      \"has-MY-humor\",\n" +
            "      \"interested-in-ons\",\n" +
            "      \"interested-in-fwb\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"has-awesome-personality\",\n" +
            "      \"is-nerdy\",\n" +
            "      \"seems-interested-in-ons-fwb-etc\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": true,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": false,\n" +
            "    \"Match-responded\": true,\n" +
            "    \"Conversation-exists\": true,\n" +
            "    \"Vibe-conversation\": 8,\n" +
            "    \"How-many-ghosts\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"timeSinceLastMessageMS\": 615972832,\n" +
            "        \"status\": \"unanswered-to-reminder\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"number\": 1,\n" +
            "        \"timeSinceLastMessageMS\": 178367256,\n" +
            "        \"status\": \"unanswered-to-reminder\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"number\": 2,\n" +
            "        \"timeSinceLastMessageMS\": 898470304,\n" +
            "        \"status\": \"unanswered-to-reminder\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Acquired-number\": true,\n" +
            "    \"Date-of-acquired-number\": null,\n" +
            "    \"Response-speed\": [\n" +
            "      {\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "        \"differenceInMS\": 768667\n" +
            "      },\n" +
            "      {\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-17T20:16:36.205Z\",\n" +
            "        \"datetimeTheirResponse\": \"2016-02-17T21:01:50.497Z\",\n" +
            "        \"differenceInMS\": 2714292\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Reminders-amount\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "        \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "        \"hasGottenReply\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"number\": 1,\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-18T22:53:55.342Z\",\n" +
            "        \"datetimeReminderSent\": \"2016-02-21T00:26:42.598Z\",\n" +
            "        \"textContentReminder\": \"Ik snap dat je overrompeld bent door de charmes van mijn laatste bericht. Geen zorgen hoor, dat begrijp ik. Neem alle tijd die je nodig hebt om weer bij te komen ;)\",\n" +
            "        \"hasGottenReply\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"number\": 2,\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-22T14:16:26.218Z\",\n" +
            "        \"datetimeReminderSent\": \"2016-03-03T23:50:56.522Z\",\n" +
            "        \"textContentReminder\": \"Heey.. Ik vond jou nu net zo gezellig en nu heb ik al meer dan een week niks van je gehoord *snif*\",\n" +
            "        \"hasGottenReply\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"number\": 3,\n" +
            "        \"datetimeMyLastMessage\": \"2016-11-01T17:32:17.238Z\",\n" +
            "        \"datetimeReminderSent\": \"2016-11-04T16:26:14.237Z\",\n" +
            "        \"textContentReminder\": \"Ging die opmerking te ver? uD83DuDE22\",\n" +
            "        \"hasGottenReply\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": true,\n" +
            "    \"Potential-click\": true,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"yeah i dated her.. but will i ever see her again?1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "    },\n" +
            "    \"No\": 2,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"Hoi match 2..\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "        \"datetime\": \"2022-02-17T19:55:02.022Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Wat fijn! Eens een begripvolle vent. Al dat gehaast maar dezer dagen. Dat kan niemand toch meer aan?\",\n" +
            "        \"datetime\": \"2022-02-17T20:07:50.689Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Ik ben slim trouwens. Aangenaam :p\",\n" +
            "        \"datetime\": \"2022-02-17T20:08:12.336Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Haha aangenaam!\",\n" +
            "        \"datetime\": \"2022-02-17T20:08:12.336Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2016-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"Cindy123\",\n" +
            "    \"Age\": 30.993107375991197,\n" +
            "    \"City\": \"Rotterdam\",\n" +
            "    \"Job\": \"Logopedist\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": true,\n" +
            "    \"Seems-fake\": false,\n" +
            "    \"Seems-empty\": false,\n" +
            "    \"Seems-obese\": false,\n" +
            "    \"Seems-toppick\": true,\n" +
            "    \"Is-uitblinker-for-Me\": null,\n" +
            "    \"Liked-me-first-is-instant-match\": null,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 3\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Bioscoop\",\n" +
            "      \"Bordspellen\",\n" +
            "      \"Lezen\",\n" +
            "      \"Disney\",\n" +
            "      \"Gamer\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [],\n" +
            "    \"Is-verified\": true,\n" +
            "    \"Amount-of-pictures\": 3,\n" +
            "    \"Attractiveness-score\": 7,\n" +
            "    \"Height\": [\n" +
            "      \"seems-short < 1.60m\",\n" +
            "      \"is-short < 1.60m\",\n" +
            "      \"seems-normal >= 1.60-1.70m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"unclear-or-no-fullbody\",\n" +
            "      \"seems-has-humor\",\n" +
            "      \"has-humor\",\n" +
            "      \"seems-has-MY-humor\",\n" +
            "      \"has-MY-humor\",\n" +
            "      \"interested-in-ons\",\n" +
            "      \"interested-in-fwb\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"has-awesome-personality\",\n" +
            "      \"is-nerdy\",\n" +
            "      \"seems-interested-in-ons-fwb-etc\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": true,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": false,\n" +
            "    \"Match-responded\": true,\n" +
            "    \"Conversation-exists\": true,\n" +
            "    \"Vibe-conversation\": 8,\n" +
            "    \"How-many-ghosts\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"timeSinceLastMessageMS\": 615972832,\n" +
            "        \"status\": \"answered\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Acquired-number\": true,\n" +
            "    \"Date-of-acquired-number\": null,\n" +
            "    \"Response-speed\": [\n" +
            "      {\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "        \"differenceInMS\": 768667\n" +
            "      },\n" +
            "      {\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-17T20:16:36.205Z\",\n" +
            "        \"datetimeTheirResponse\": \"2016-02-17T21:01:50.497Z\",\n" +
            "        \"differenceInMS\": 2714292\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Reminders-amount\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "        \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "        \"hasGottenReply\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": true,\n" +
            "    \"Potential-click\": true,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"yeah i dated her.. but will i ever see her again?1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "    },\n" +
            "    \"No\": 3,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"Hoi match 3!\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2016-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"Priscilla123\",\n" +
            "    \"Age\": 23,\n" +
            "    \"City\": \"Rotterdam\",\n" +
            "    \"Job\": \"Docente\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": true,\n" +
            "    \"Seems-fake\": false,\n" +
            "    \"Seems-empty\": false,\n" +
            "    \"Seems-obese\": true,\n" +
            "    \"Seems-toppick\": true,\n" +
            "    \"Is-uitblinker-for-Me\": null,\n" +
            "    \"Liked-me-first-is-instant-match\": null,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 3\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Bioscoop\",\n" +
            "      \"Bordspellen\",\n" +
            "      \"Lezen\",\n" +
            "      \"Disney\",\n" +
            "      \"Gamer\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [],\n" +
            "    \"Is-verified\": true,\n" +
            "    \"Amount-of-pictures\": 3,\n" +
            "    \"Attractiveness-score\": 6,\n" +
            "    \"Height\": [\n" +
            "      \"seems-short < 1.60m\",\n" +
            "      \"is-short < 1.60m\",\n" +
            "      \"seems-normal >= 1.60-1.70m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"unclear-or-no-fullbody\",\n" +
            "      \"seems-has-humor\",\n" +
            "      \"has-humor\",\n" +
            "      \"seems-has-MY-humor\",\n" +
            "      \"has-MY-humor\",\n" +
            "      \"interested-in-ons\",\n" +
            "      \"interested-in-fwb\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"has-awesome-personality\",\n" +
            "      \"is-nerdy\",\n" +
            "      \"seems-interested-in-ons-fwb-etc\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": true,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": false,\n" +
            "    \"Match-responded\": true,\n" +
            "    \"Conversation-exists\": true,\n" +
            "    \"Vibe-conversation\": 8,\n" +
            "    \"How-many-ghosts\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"timeSinceLastMessageMS\": 615972832,\n" +
            "        \"status\": \"answered\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Acquired-number\": true,\n" +
            "    \"Date-of-acquired-number\": null,\n" +
            "    \"Response-speed\": [\n" +
            "      {\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "        \"differenceInMS\": 768667\n" +
            "      },\n" +
            "      {\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-17T20:16:36.205Z\",\n" +
            "        \"datetimeTheirResponse\": \"2016-02-17T21:01:50.497Z\",\n" +
            "        \"differenceInMS\": 2714292\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Reminders-amount\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "        \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "        \"hasGottenReply\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": true,\n" +
            "    \"Potential-click\": true,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"Notes for priscilla123\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"1228ce2770640a14b0f45007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"1229313f0e8ed4f6c0e45002a\"\n" +
            "    },\n" +
            "    \"No\": 4,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"Hoi match 4!\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2017-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"VerzorgendeMeid123\",\n" +
            "    \"Age\": 32.325354,\n" +
            "    \"City\": \"Dordrecht\",\n" +
            "    \"Job\": \"Docente\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": true,\n" +
            "    \"Seems-fake\": false,\n" +
            "    \"Seems-empty\": false,\n" +
            "    \"Seems-obese\": false,\n" +
            "    \"Seems-toppick\": false,\n" +
            "    \"Is-uitblinker-for-Me\": null,\n" +
            "    \"Liked-me-first-is-instant-match\": null,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 3\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"Verzorgende aan Erasmus MC\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Uitgaan\",\n" +
            "      \"Sporten\",\n" +
            "      \"Schrijven\",\n" +
            "      \"Eten\",\n" +
            "      \"Koken\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [],\n" +
            "    \"Is-verified\": true,\n" +
            "    \"Amount-of-pictures\": 3,\n" +
            "    \"Attractiveness-score\": 6,\n" +
            "    \"Height\": [\n" +
            "      \"seems-tall > 1.80m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"seems-sweet\",\n" +
            "      \"seems-has-humor\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"has-awesome-personality\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": true,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": false,\n" +
            "    \"Match-responded\": true,\n" +
            "    \"Conversation-exists\": false,\n" +
            "    \"Vibe-conversation\": 6,\n" +
            "    \"How-many-ghosts\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"timeSinceLastMessageMS\": 615972832,\n" +
            "        \"status\": \"answered\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Acquired-number\": true,\n" +
            "    \"Date-of-acquired-number\": null,\n" +
            "    \"Response-speed\": [],\n" +
            "    \"Reminders-amount\": [],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": false,\n" +
            "    \"Potential-click\": false,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"Notes for verzorgendeMeid123\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "    },\n" +
            "    \"No\": 5,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"Hoi match 3!\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"Hoi daar!\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:59.190Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2017-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"SnelleReageerder123\",\n" +
            "    \"Age\": 23.56456,\n" +
            "    \"City\": \"Rotterdam\",\n" +
            "    \"Job\": \"Docente\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": false,\n" +
            "    \"Seems-fake\": true,\n" +
            "    \"Seems-empty\": true,\n" +
            "    \"Seems-obese\": false,\n" +
            "    \"Seems-toppick\": true,\n" +
            "    \"Is-uitblinker-for-Me\": true,\n" +
            "    \"Liked-me-first-is-instant-match\": false,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 3\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Hardlopen\",\n" +
            "      \"Sporten\",\n" +
            "      \"Boksen\",\n" +
            "      \"Basicfit\",\n" +
            "      \"Influencer\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [],\n" +
            "    \"Is-verified\": true,\n" +
            "    \"Amount-of-pictures\": 3,\n" +
            "    \"Attractiveness-score\": 8,\n" +
            "    \"Height\": [\n" +
            "      \"seems-normal >= 1.60-1.70m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"seems-has-humor\",\n" +
            "      \"has-humor\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"has-awesome-personality\",\n" +
            "      \"is-nerdy\",\n" +
            "      \"seems-interested-in-ons-fwb-etc\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": true,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": false,\n" +
            "    \"Match-responded\": true,\n" +
            "    \"Conversation-exists\": true,\n" +
            "    \"Vibe-conversation\": 6,\n" +
            "    \"How-many-ghosts\": [],\n" +
            "    \"Acquired-number\": true,\n" +
            "    \"Date-of-acquired-number\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Response-speed\": [\n" +
            "      {\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "        \"differenceInMS\": 768667\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Reminders-amount\": [\n" +
            "      {\n" +
            "        \"number\": 0,\n" +
            "        \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "        \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "        \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "        \"hasGottenReply\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": false,\n" +
            "    \"Potential-click\": false,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"Notes for snelle reageerster123\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"555ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"555313f0e8ed4f6c0e00002a\"\n" +
            "    },\n" +
            "    \"No\": 6,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"yo join my fake profile on shady website\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"no thank you\",\n" +
            "        \"datetime\": \"2022-02-10T17:48:59.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2018-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"TopPickGhosting123\",\n" +
            "    \"Age\": 26.6758,\n" +
            "    \"City\": \"Utrecht\",\n" +
            "    \"Job\": \"Instagrammodel\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": false,\n" +
            "    \"Seems-fake\": false,\n" +
            "    \"Seems-empty\": false,\n" +
            "    \"Seems-obese\": false,\n" +
            "    \"Seems-toppick\": true,\n" +
            "    \"Is-uitblinker-for-Me\": true,\n" +
            "    \"Liked-me-first-is-instant-match\": true,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 56\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"LBO\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Beauty\",\n" +
            "      \"Instagram\",\n" +
            "      \"Fotografie\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [\n" +
            "      \"\\\"superlike\\\"\"\n" +
            "    ],\n" +
            "    \"Is-verified\": false,\n" +
            "    \"Amount-of-pictures\": 12,\n" +
            "    \"Attractiveness-score\": 9,\n" +
            "    \"Height\": [\n" +
            "      \"seems-normal >= 1.60-1.70m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"has-big-*****-not-obese\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"seems-bitchy\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": false,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": true,\n" +
            "    \"Match-responded\": false,\n" +
            "    \"Conversation-exists\": true,\n" +
            "    \"Vibe-conversation\": 3,\n" +
            "    \"How-many-ghosts\": [],\n" +
            "    \"Acquired-number\": false,\n" +
            "    \"Date-of-acquired-number\": null,\n" +
            "    \"Response-speed\": [],\n" +
            "    \"Reminders-amount\": [],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": false,\n" +
            "    \"Potential-click\": false,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"Notes for snelle TopPickGhosting123\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"775ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"775313f0e8ed4f6c0e00002a\"\n" +
            "    },\n" +
            "    \"No\": 7,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"hello how are you?\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"you alive?\",\n" +
            "        \"datetime\": \"2022-02-10T17:48:59.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"helloooo?\",\n" +
            "        \"datetime\": \"2022-02-11T17:48:59.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2019-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"GhostingGirl123\",\n" +
            "    \"Age\": 27,\n" +
            "    \"City\": \"Amsterdam\",\n" +
            "    \"Job\": \"Instagrammodel2\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": true,\n" +
            "    \"Seems-fake\": false,\n" +
            "    \"Seems-empty\": false,\n" +
            "    \"Seems-obese\": false,\n" +
            "    \"Seems-toppick\": true,\n" +
            "    \"Is-uitblinker-for-Me\": true,\n" +
            "    \"Liked-me-first-is-instant-match\": true,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 12\n" +
            "      },\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 34\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"University of Cambridge\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Beauty\",\n" +
            "      \"Instagram\",\n" +
            "      \"Fotografie\",\n" +
            "      \"Voetbal\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [\n" +
            "      \"\\\"like\\\"\"\n" +
            "    ],\n" +
            "    \"Is-verified\": true,\n" +
            "    \"Amount-of-pictures\": 5,\n" +
            "    \"Attractiveness-score\": 8,\n" +
            "    \"Height\": [\n" +
            "      \"seems-normal >= 1.60-1.70m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"has-big-*****-not-obese\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"seems-bitchy\",\n" +
            "      \"seems-nerdy\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": true,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": false,\n" +
            "    \"Match-responded\": false,\n" +
            "    \"Conversation-exists\": true,\n" +
            "    \"Vibe-conversation\": 6,\n" +
            "    \"How-many-ghosts\": [],\n" +
            "    \"Acquired-number\": false,\n" +
            "    \"Date-of-acquired-number\": null,\n" +
            "    \"Response-speed\": [],\n" +
            "    \"Reminders-amount\": [],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": false,\n" +
            "    \"Potential-click\": false,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"Notes for snelle GhostingGirl123\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"System-no\": {\n" +
            "      \"appType\": \"tinder\",\n" +
            "      \"id\": \"715ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "      \"tempId\": \"715313f0e8ed4f6c0e00002a\"\n" +
            "    },\n" +
            "    \"No\": 8,\n" +
            "    \"Messages\": [\n" +
            "      {\n" +
            "        \"message\": \"hello how are you?\",\n" +
            "        \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"hey there\",\n" +
            "        \"datetime\": \"2022-02-10T17:48:59.190Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"hi :)!\",\n" +
            "        \"datetime\": \"2022-02-11T17:48:59.190Z\",\n" +
            "        \"author\": \"me\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"message\": \"how are you?\",\n" +
            "        \"datetime\": \"2022-02-12T17:48:59.190Z\",\n" +
            "        \"author\": \"match\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "    \"Date-liked-or-passed\": \"2020-09-08T12:53:23.385Z\",\n" +
            "    \"Name\": \"AnotherNiceGirl123\",\n" +
            "    \"Age\": 28.1223,\n" +
            "    \"City\": \"Tilburg\",\n" +
            "    \"Job\": \"Verkoopster\",\n" +
            "    \"Has-profiletext\": true,\n" +
            "    \"Has-usefull-profiletext\": false,\n" +
            "    \"Seems-fake\": false,\n" +
            "    \"Seems-empty\": false,\n" +
            "    \"Seems-obese\": true,\n" +
            "    \"Seems-toppick\": false,\n" +
            "    \"Is-uitblinker-for-Me\": false,\n" +
            "    \"Liked-me-first-is-instant-match\": true,\n" +
            "    \"Distance-in-km\": [\n" +
            "      {\n" +
            "        \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "        \"distanceInKM\": 15\n" +
            "      }\n" +
            "    ],\n" +
            "    \"School\": \"Hogeschool van Utrecht\",\n" +
            "    \"Gender\": \"Female\",\n" +
            "    \"Interests\": [\n" +
            "      \"Borrelen\",\n" +
            "      \"Bordspelletjes\",\n" +
            "      \"Tv kijken\"\n" +
            "    ],\n" +
            "    \"Type-of-match-or-like\": [\n" +
            "      \"\\\"like\\\"\"\n" +
            "    ],\n" +
            "    \"Is-verified\": true,\n" +
            "    \"Amount-of-pictures\": 5,\n" +
            "    \"Attractiveness-score\": 7,\n" +
            "    \"Height\": [\n" +
            "      \"seems-normal >= 1.60-1.70m\"\n" +
            "    ],\n" +
            "    \"Details-tags\": [\n" +
            "      \"has-big-*****-not-obese\"\n" +
            "    ],\n" +
            "    \"Vibe-tags\": [\n" +
            "      \"seems-sweet\",\n" +
            "      \"seems-nerdy\"\n" +
            "    ],\n" +
            "    \"Seems-to-be-active\": true,\n" +
            "    \"Did-i-like\": true,\n" +
            "    \"Is-match\": true,\n" +
            "    \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "    \"Match-sent-first-message\": false,\n" +
            "    \"Match-responded\": true,\n" +
            "    \"Conversation-exists\": true,\n" +
            "    \"Vibe-conversation\": 7,\n" +
            "    \"How-many-ghosts\": [],\n" +
            "    \"Acquired-number\": false,\n" +
            "    \"Date-of-acquired-number\": null,\n" +
            "    \"Response-speed\": [],\n" +
            "    \"Reminders-amount\": [],\n" +
            "    \"Match-wants-no-contact\": null,\n" +
            "    \"Blocked-or-removed\": null,\n" +
            "    \"Date-of-unmatch\": null,\n" +
            "    \"Seemingly-deleted-profile\": null,\n" +
            "    \"Interested-in-sex\": false,\n" +
            "    \"Potential-click\": false,\n" +
            "    \"Why-i-removed\": [],\n" +
            "    \"Did-i-unmatch\": false,\n" +
            "    \"Notes\": \"Notes for snelle AnotherNiceGirl123\"\n" +
            "  }\n" +
            "]";
}
