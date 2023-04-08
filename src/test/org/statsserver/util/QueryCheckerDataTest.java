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
    private static final String fourResultsJson = "[{\n" +
            "        \"System-no\": {\n" +
            "            \"appType\": \"tinder\",\n" +
            "            \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "            \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "        },\n" +
            "        \"No\": 1,\n" +
            "        \"Messages\": [{\n" +
            "                \"message\": \"\\\"eigenwijs blondje\\\" schrijf je op je profiel.. Betekent dat jij een slim blondje bent of een dom blondje?\",\n" +
            "                \"datetime\": \"2016-02-10T16:48:49.190Z\",\n" +
            "                \"author\": \"me\"\n" +
            "            }, {\n" +
            "                \"message\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "                \"datetime\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"author\": \"me\"\n" +
            "            }, {\n" +
            "                \"message\": \"Wat fijn! Eens een begripvolle vent. Al dat gehaast maar dezer dagen. Dat kan niemand toch meer aan?\",\n" +
            "                \"datetime\": \"2016-02-17T20:07:50.689Z\",\n" +
            "                \"author\": \"match\"\n" +
            "            }, {\n" +
            "                \"message\": \"Ik ben slim trouwens. Aangenaam :p\",\n" +
            "                \"datetime\": \"2016-02-17T20:08:12.336Z\",\n" +
            "                \"author\": \"match\"\n" +
            "            },\n" +
            "\t\t\t{\n" +
            "                \"message\": \"Ik ben slim trouwens. Aangenaam :p\",\n" +
            "                \"datetime\": \"2016-02-17T20:08:12.336Z\",\n" +
            "                \"author\": \"match\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "        \"Date-liked-or-passed\": \"2022-09-08T12:53:23.385Z\",\n" +
            "        \"Name\": \"Daphne123\",\n" +
            "        \"Age\": 30.993107375991197,\n" +
            "        \"City\": \"Roosendaal\",\n" +
            "        \"Job\": \"Logopedist\",\n" +
            "        \"Has-profiletext\": true,\n" +
            "        \"Has-usefull-profiletext\": true,\n" +
            "        \"Seems-fake\": false,\n" +
            "        \"Seems-empty\": false,\n" +
            "        \"Seems-obese\": false,\n" +
            "        \"Seems-toppick\": true,\n" +
            "        \"Is-uitblinker-for-Me\": null,\n" +
            "        \"Liked-me-first-is-instant-match\": null,\n" +
            "        \"Distance-in-km\": [{\n" +
            "                \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "                \"distanceInKM\": 3\n" +
            "            }\n" +
            "        ],\n" +
            "        \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "        \"Gender\": \"Female\",\n" +
            "        \"Interests\": [\"Bioscoop\", \"Bordspellen\", \"Lezen\", \"Disney\", \"Gamer\"],\n" +
            "        \"Type-of-match-or-like\": [],\n" +
            "        \"Is-verified\": true,\n" +
            "        \"Amount-of-pictures\": 3,\n" +
            "        \"Attractiveness-score\": 7,\n" +
            "        \"Height\": [\"seems-short < 1.60m\", \"is-short < 1.60m\", \"seems-normal >= 1.60-1.70m\"],\n" +
            "        \"Details-tags\": [\"unclear-or-no-fullbody\", \"seems-has-humor\", \"has-humor\", \"seems-has-MY-humor\", \"has-MY-humor\", \"interested-in-ons\", \"interested-in-fwb\"],\n" +
            "        \"Vibe-tags\": [\"has-awesome-personality\", \"is-nerdy\", \"seems-interested-in-ons-fwb-etc\"],\n" +
            "        \"Seems-to-be-active\": true,\n" +
            "        \"Did-i-like\": true,\n" +
            "        \"Is-match\": true,\n" +
            "        \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "        \"Match-sent-first-message\": false,\n" +
            "        \"Match-responded\": true,\n" +
            "        \"Conversation-exists\": true,\n" +
            "        \"Vibe-conversation\": 8,\n" +
            "        \"How-many-ghosts\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"timeSinceLastMessageMS\": 615972832,\n" +
            "                \"status\": \"unanswered-to-reminder\"\n" +
            "            }, {\n" +
            "                \"number\": 1,\n" +
            "                \"timeSinceLastMessageMS\": 178367256,\n" +
            "                \"status\": \"unanswered-to-reminder\"\n" +
            "            }, {\n" +
            "                \"number\": 2,\n" +
            "                \"timeSinceLastMessageMS\": 898470304,\n" +
            "                \"status\": \"unanswered-to-reminder\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Acquired-number\": true,\n" +
            "        \"Date-of-acquired-number\": null,\n" +
            "        \"Response-speed\": [{\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "                \"differenceInMS\": 768667\n" +
            "            }, {\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T20:16:36.205Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T21:01:50.497Z\",\n" +
            "                \"differenceInMS\": 2714292\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Reminders-amount\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "                \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "                \"hasGottenReply\": true\n" +
            "            }, {\n" +
            "                \"number\": 1,\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-18T22:53:55.342Z\",\n" +
            "                \"datetimeReminderSent\": \"2016-02-21T00:26:42.598Z\",\n" +
            "                \"textContentReminder\": \"Ik snap dat je overrompeld bent door de charmes van mijn laatste bericht. Geen zorgen hoor, dat begrijp ik. Neem alle tijd die je nodig hebt om weer bij te komen ;)\",\n" +
            "                \"hasGottenReply\": true\n" +
            "            }, {\n" +
            "                \"number\": 2,\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-22T14:16:26.218Z\",\n" +
            "                \"datetimeReminderSent\": \"2016-03-03T23:50:56.522Z\",\n" +
            "                \"textContentReminder\": \"Heey.. Ik vond jou nu net zo gezellig en nu heb ik al meer dan een week niks van je gehoord *snif*\",\n" +
            "                \"hasGottenReply\": true\n" +
            "            }, {\n" +
            "                \"number\": 3,\n" +
            "                \"datetimeMyLastMessage\": \"2016-11-01T17:32:17.238Z\",\n" +
            "                \"datetimeReminderSent\": \"2016-11-04T16:26:14.237Z\",\n" +
            "                \"textContentReminder\": \"Ging die opmerking te ver? \uD83D\uDE22\",\n" +
            "                \"hasGottenReply\": true\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Match-wants-no-contact\": null,\n" +
            "        \"Blocked-or-removed\": null,\n" +
            "        \"Date-of-unmatch\": null,\n" +
            "        \"Seemingly-deleted-profile\": null,\n" +
            "        \"Interested-in-sex\": true,\n" +
            "        \"Potential-click\": true,\n" +
            "        \"Why-i-removed\": [],\n" +
            "        \"Did-i-unmatch\": false,\n" +
            "        \"Notes\": \"yeah i dated her.. but will i ever see her again?1\"\n" +
            "    },\n" +
            "\t{\n" +
            "        \"System-no\": {\n" +
            "            \"appType\": \"tinder\",\n" +
            "            \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "            \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "        },\n" +
            "        \"No\": 2,\n" +
            "        \"Messages\": [{\n" +
            "                \"message\": \"Hoi match 2..\",\n" +
            "                \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "                \"author\": \"me\"\n" +
            "            }, {\n" +
            "                \"message\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "                \"datetime\": \"2022-02-17T19:55:02.022Z\",\n" +
            "                \"author\": \"me\"\n" +
            "            }, {\n" +
            "                \"message\": \"Wat fijn! Eens een begripvolle vent. Al dat gehaast maar dezer dagen. Dat kan niemand toch meer aan?\",\n" +
            "                \"datetime\": \"2022-02-17T20:07:50.689Z\",\n" +
            "                \"author\": \"match\"\n" +
            "            }, {\n" +
            "                \"message\": \"Ik ben slim trouwens. Aangenaam :p\",\n" +
            "                \"datetime\": \"2022-02-17T20:08:12.336Z\",\n" +
            "                \"author\": \"match\"\n" +
            "            },\n" +
            "\t\t\t{\n" +
            "                \"message\": \"Haha aangenaam!\",\n" +
            "                \"datetime\": \"2022-02-17T20:08:12.336Z\",\n" +
            "                \"author\": \"me\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "        \"Date-liked-or-passed\": \"2022-09-08T12:53:23.385Z\",\n" +
            "        \"Name\": \"Cindy123\",\n" +
            "        \"Age\": 30.993107375991197,\n" +
            "        \"City\": \"Rotterdam\",\n" +
            "        \"Job\": \"Logopedist\",\n" +
            "        \"Has-profiletext\": true,\n" +
            "        \"Has-usefull-profiletext\": true,\n" +
            "        \"Seems-fake\": false,\n" +
            "        \"Seems-empty\": false,\n" +
            "        \"Seems-obese\": false,\n" +
            "        \"Seems-toppick\": true,\n" +
            "        \"Is-uitblinker-for-Me\": null,\n" +
            "        \"Liked-me-first-is-instant-match\": null,\n" +
            "        \"Distance-in-km\": [{\n" +
            "                \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "                \"distanceInKM\": 3\n" +
            "            }\n" +
            "        ],\n" +
            "        \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "        \"Gender\": \"Female\",\n" +
            "        \"Interests\": [\"Bioscoop\", \"Bordspellen\", \"Lezen\", \"Disney\", \"Gamer\"],\n" +
            "        \"Type-of-match-or-like\": [],\n" +
            "        \"Is-verified\": true,\n" +
            "        \"Amount-of-pictures\": 3,\n" +
            "        \"Attractiveness-score\": 7,\n" +
            "        \"Height\": [\"seems-short < 1.60m\", \"is-short < 1.60m\", \"seems-normal >= 1.60-1.70m\"],\n" +
            "        \"Details-tags\": [\"unclear-or-no-fullbody\", \"seems-has-humor\", \"has-humor\", \"seems-has-MY-humor\", \"has-MY-humor\", \"interested-in-ons\", \"interested-in-fwb\"],\n" +
            "        \"Vibe-tags\": [\"has-awesome-personality\", \"is-nerdy\", \"seems-interested-in-ons-fwb-etc\"],\n" +
            "        \"Seems-to-be-active\": true,\n" +
            "        \"Did-i-like\": true,\n" +
            "        \"Is-match\": true,\n" +
            "        \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "        \"Match-sent-first-message\": false,\n" +
            "        \"Match-responded\": true,\n" +
            "        \"Conversation-exists\": true,\n" +
            "        \"Vibe-conversation\": 8,\n" +
            "        \"How-many-ghosts\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"timeSinceLastMessageMS\": 615972832,\n" +
            "                \"status\": \"answered\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Acquired-number\": true,\n" +
            "        \"Date-of-acquired-number\": null,\n" +
            "        \"Response-speed\": [{\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "                \"differenceInMS\": 768667\n" +
            "            }, {\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T20:16:36.205Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T21:01:50.497Z\",\n" +
            "                \"differenceInMS\": 2714292\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Reminders-amount\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "                \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "                \"hasGottenReply\": true\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Match-wants-no-contact\": null,\n" +
            "        \"Blocked-or-removed\": null,\n" +
            "        \"Date-of-unmatch\": null,\n" +
            "        \"Seemingly-deleted-profile\": null,\n" +
            "        \"Interested-in-sex\": true,\n" +
            "        \"Potential-click\": true,\n" +
            "        \"Why-i-removed\": [],\n" +
            "        \"Did-i-unmatch\": false,\n" +
            "        \"Notes\": \"yeah i dated her.. but will i ever see her again?1\"\n" +
            "    },\n" +
            "\t{\n" +
            "        \"System-no\": {\n" +
            "            \"appType\": \"tinder\",\n" +
            "            \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "            \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "        },\n" +
            "        \"No\": 3,\n" +
            "        \"Messages\": [{\n" +
            "                \"message\": \"Hoi match 3!\",\n" +
            "                \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "                \"author\": \"me\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "        \"Date-liked-or-passed\": \"2022-09-08T12:53:23.385Z\",\n" +
            "        \"Name\": \"Priscilla123\",\n" +
            "        \"Age\": 23,\n" +
            "        \"City\": \"Rotterdam\",\n" +
            "        \"Job\": \"Docente\",\n" +
            "        \"Has-profiletext\": true,\n" +
            "        \"Has-usefull-profiletext\": true,\n" +
            "        \"Seems-fake\": false,\n" +
            "        \"Seems-empty\": false,\n" +
            "        \"Seems-obese\": true,\n" +
            "        \"Seems-toppick\": true,\n" +
            "        \"Is-uitblinker-for-Me\": null,\n" +
            "        \"Liked-me-first-is-instant-match\": null,\n" +
            "        \"Distance-in-km\": [{\n" +
            "                \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "                \"distanceInKM\": 3\n" +
            "            }\n" +
            "        ],\n" +
            "        \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "        \"Gender\": \"Female\",\n" +
            "        \"Interests\": [\"Bioscoop\", \"Bordspellen\", \"Lezen\", \"Disney\", \"Gamer\"],\n" +
            "        \"Type-of-match-or-like\": [],\n" +
            "        \"Is-verified\": true,\n" +
            "        \"Amount-of-pictures\": 3,\n" +
            "        \"Attractiveness-score\": 6,\n" +
            "        \"Height\": [\"seems-short < 1.60m\", \"is-short < 1.60m\", \"seems-normal >= 1.60-1.70m\"],\n" +
            "        \"Details-tags\": [\"unclear-or-no-fullbody\", \"seems-has-humor\", \"has-humor\", \"seems-has-MY-humor\", \"has-MY-humor\", \"interested-in-ons\", \"interested-in-fwb\"],\n" +
            "        \"Vibe-tags\": [\"has-awesome-personality\", \"is-nerdy\", \"seems-interested-in-ons-fwb-etc\"],\n" +
            "        \"Seems-to-be-active\": true,\n" +
            "        \"Did-i-like\": true,\n" +
            "        \"Is-match\": true,\n" +
            "        \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "        \"Match-sent-first-message\": false,\n" +
            "        \"Match-responded\": true,\n" +
            "        \"Conversation-exists\": true,\n" +
            "        \"Vibe-conversation\": 8,\n" +
            "        \"How-many-ghosts\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"timeSinceLastMessageMS\": 615972832,\n" +
            "                \"status\": \"answered\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Acquired-number\": true,\n" +
            "        \"Date-of-acquired-number\": null,\n" +
            "        \"Response-speed\": [{\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "                \"differenceInMS\": 768667\n" +
            "            }, {\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T20:16:36.205Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T21:01:50.497Z\",\n" +
            "                \"differenceInMS\": 2714292\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Reminders-amount\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "                \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "                \"hasGottenReply\": true\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Match-wants-no-contact\": null,\n" +
            "        \"Blocked-or-removed\": null,\n" +
            "        \"Date-of-unmatch\": null,\n" +
            "        \"Seemingly-deleted-profile\": null,\n" +
            "        \"Interested-in-sex\": true,\n" +
            "        \"Potential-click\": true,\n" +
            "        \"Why-i-removed\": [],\n" +
            "        \"Did-i-unmatch\": false,\n" +
            "        \"Notes\": \"Notes for priscilla123\"\n" +
            "    },\n" +
            "\t{\n" +
            "        \"System-no\": {\n" +
            "            \"appType\": \"tinder\",\n" +
            "            \"id\": \"528ce2770640a14b0f00007c529313f0e8ed4f6c0e00002a\",\n" +
            "            \"tempId\": \"529313f0e8ed4f6c0e00002a\"\n" +
            "        },\n" +
            "        \"No\": 4,\n" +
            "        \"Messages\": [{\n" +
            "                \"message\": \"Hoi match 3!\",\n" +
            "                \"datetime\": \"2022-02-10T16:48:49.190Z\",\n" +
            "                \"author\": \"me\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Last-updated\": \"2022-09-11T12:53:23.385Z\",\n" +
            "        \"Date-liked-or-passed\": \"2022-09-08T12:53:23.385Z\",\n" +
            "        \"Name\": \"Vivian123\",\n" +
            "        \"Age\": 32.325354,\n" +
            "        \"City\": \"Dordrecht\",\n" +
            "        \"Job\": \"Docente\",\n" +
            "        \"Has-profiletext\": true,\n" +
            "        \"Has-usefull-profiletext\": true,\n" +
            "        \"Seems-fake\": false,\n" +
            "        \"Seems-empty\": false,\n" +
            "        \"Seems-obese\": true,\n" +
            "        \"Seems-toppick\": true,\n" +
            "        \"Is-uitblinker-for-Me\": null,\n" +
            "        \"Liked-me-first-is-instant-match\": null,\n" +
            "        \"Distance-in-km\": [{\n" +
            "                \"dateTime\": \"2022-03-20T19:39:07.046Z\",\n" +
            "                \"distanceInKM\": 3\n" +
            "            }\n" +
            "        ],\n" +
            "        \"School\": \"Logopedie aan de Hogeschool Rotterdam\",\n" +
            "        \"Gender\": \"Female\",\n" +
            "        \"Interests\": [\"Bioscoop\", \"Bordspellen\", \"Lezen\", \"Disney\", \"Gamer\"],\n" +
            "        \"Type-of-match-or-like\": [],\n" +
            "        \"Is-verified\": true,\n" +
            "        \"Amount-of-pictures\": 3,\n" +
            "        \"Attractiveness-score\": 6,\n" +
            "        \"Height\": [\"seems-short < 1.60m\", \"is-short < 1.60m\", \"seems-normal >= 1.60-1.70m\"],\n" +
            "        \"Details-tags\": [\"unclear-or-no-fullbody\", \"seems-has-humor\", \"has-humor\", \"seems-has-MY-humor\", \"has-MY-humor\", \"interested-in-ons\", \"interested-in-fwb\"],\n" +
            "        \"Vibe-tags\": [\"has-awesome-personality\", \"is-nerdy\", \"seems-interested-in-ons-fwb-etc\"],\n" +
            "        \"Seems-to-be-active\": true,\n" +
            "        \"Did-i-like\": true,\n" +
            "        \"Is-match\": true,\n" +
            "        \"Date-match\": \"2016-02-10T16:41:22.047Z\",\n" +
            "        \"Match-sent-first-message\": false,\n" +
            "        \"Match-responded\": true,\n" +
            "        \"Conversation-exists\": true,\n" +
            "        \"Vibe-conversation\": 8,\n" +
            "        \"How-many-ghosts\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"timeSinceLastMessageMS\": 615972832,\n" +
            "                \"status\": \"answered\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Acquired-number\": true,\n" +
            "        \"Date-of-acquired-number\": null,\n" +
            "        \"Response-speed\": [{\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T20:07:50.689Z\",\n" +
            "                \"differenceInMS\": 768667\n" +
            "            }, {\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-17T20:16:36.205Z\",\n" +
            "                \"datetimeTheirResponse\": \"2016-02-17T21:01:50.497Z\",\n" +
            "                \"differenceInMS\": 2714292\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Reminders-amount\": [{\n" +
            "                \"number\": 0,\n" +
            "                \"datetimeMyLastMessage\": \"2016-02-10T16:48:49.190Z\",\n" +
            "                \"datetimeReminderSent\": \"2016-02-17T19:55:02.022Z\",\n" +
            "                \"textContentReminder\": \"Ik snap dat mijn woorden zo charmerend zijn dat ze jou zo overrompelen dat je gewoon tijd nodig hebt om er boven op te komen. Geen zorgen hoor, daar heb ik begrip voor! Neem alle tijd die je nodig hebt!\",\n" +
            "                \"hasGottenReply\": true\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Match-wants-no-contact\": null,\n" +
            "        \"Blocked-or-removed\": null,\n" +
            "        \"Date-of-unmatch\": null,\n" +
            "        \"Seemingly-deleted-profile\": null,\n" +
            "        \"Interested-in-sex\": true,\n" +
            "        \"Potential-click\": true,\n" +
            "        \"Why-i-removed\": [],\n" +
            "        \"Did-i-unmatch\": false,\n" +
            "        \"Notes\": \"Notes for priscilla123\"\n" +
            "    }]";
}
