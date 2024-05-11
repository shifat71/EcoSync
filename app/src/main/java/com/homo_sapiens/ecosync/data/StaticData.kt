package com.homo_sapiens.ecosync.data

import com.homo_sapiens.ecosync.data.model.announcement.Announcement

val announcement0 = Announcement(
    title = "Dumping Stations in Uttara will be closed for 2 days",
    date = "2023-12-06",
    sender = "EcoSync Team",
    activeDateRange = "2023-12-06 to 2023-12-08",
    content = "Please be informed that the dumping stations in Uttara will remain closed for the next 2 days. Let's utilize this opportunity to dispose of our waste responsibly.",
    attachment = "https://example.com/dumping-stations"
)

val announcement1 = Announcement(
    title = "Dumping Track will be 2 hours late at Banani STS",
    date = "2023-12-06",
    sender = "EcoSync Team",
    activeDateRange = "2023-12-06 to 2023-12-08",
    content = "Due to unforeseen circumstances, the regular waste collection service at Banani STS will experience a delay today. The dumping track is expected to be 2 hours late. We apologize for any inconvenience caused and appreciate your understanding and cooperation in this matter. Please ensure your waste is ready for collection as usual, and it will be collected as soon as possible. Thank you for your patience.",
    attachment = "https://example.com/dumping-stations"
)

val announcement2 = Announcement(
    title = "New Recycling Initiative",
    date = "2023-12-01",
    sender = "EcoSync Team",
    activeDateRange = "2023-12-01 to 2023-12-31",
    content = "We're excited to announce a new recycling initiative starting this month. Let's work together to reduce waste and protect our environment!",
    attachment = "https://example.com/recycling-initiative"
)

val announcement3 = Announcement(
    title = "Community Cleanup Event",
    date = "2023-12-02",
    sender = "EcoSync Team",
    activeDateRange = "2023-12-02 to 2023-12-31",
    content = "Join us for a community cleanup event this weekend. Let's keep our community clean and green!",
    attachment = "https://example.com/cleanup-event"
)

val announcement4 = Announcement(
    title = "Sustainability Workshop",
    date = "2023-12-03",
    sender = "EcoSync Team",
    activeDateRange = "2023-12-03 to 2023-12-31",
    content = "Don't miss our upcoming sustainability workshop where we'll share tips and strategies for living a more eco-friendly lifestyle.",
    attachment = "https://example.com/sustainability-workshop"
)

val announcement5 = Announcement(
    title = "Eco-Friendly Product Fair",
    date = "2023-12-04",
    sender = "EcoSync Team",
    activeDateRange = "2023-12-04 to 2023-12-31",
    content = "Visit our eco-friendly product fair to discover a range of products that are good for you and the planet.",
    attachment = "https://example.com/product-fair"
)

val announcement6 = Announcement(
    title = "Year-End Reflections and Look Ahead",
    date = "2023-12-05",
    sender = "EcoSync Team",
    activeDateRange = "2023-12-05 to 2023-12-31",
    content = "As the year comes to a close, join us in reflecting on our achievements and looking ahead to our goals for the new year.",
    attachment = "https://example.com/year-end-reflections"
)

val announcementList = listOf(announcement0, announcement1, announcement2, announcement3, announcement4, announcement5, announcement6)
