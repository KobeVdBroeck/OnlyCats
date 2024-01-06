package com.examenopdracht.onlycats.mock

import com.examenopdracht.onlycats.network.CatPhoto

class FakeDataSource {
    var images: List<CatPhoto> = mutableListOf(
        CatPhoto(1, "a1b2", "https://example.com/cat1.jpg", 200, 200),
        CatPhoto(2, "c3d4", "https://example.com/cat2.jpg", 250, 180),
        CatPhoto(3, "e5f6", "https://example.com/cat3.jpg", 180, 220),
        CatPhoto(4, "g7h8", "https://example.com/cat4.jpg", 220, 240),
        CatPhoto(5, "i9j0", "https://example.com/cat5.jpg", 190, 200),
        CatPhoto(6, "k1l2", "https://example.com/cat6.jpg", 210, 180),
        CatPhoto(7, "m3n4", "https://example.com/cat7.jpg", 230, 220),
        CatPhoto(8, "o5p6", "https://example.com/cat8.jpg", 250, 190),
        CatPhoto(9, "q7r8", "https://example.com/cat9.jpg", 180, 210),
        CatPhoto(10, "s9t0", "https://example.com/cat10.jpg", 200, 220),
        CatPhoto(11, "u1v2", "https://example.com/cat11.jpg", 240, 200),
        CatPhoto(12, "w3x4", "https://example.com/cat12.jpg", 180, 250),
        CatPhoto(13, "y5z6", "https://example.com/cat13.jpg", 210, 180),
        CatPhoto(14, "a1b2c3", "https://example.com/cat14.jpg", 220, 240),
        CatPhoto(15, "d4e5f6", "https://example.com/cat15.jpg", 190, 200),
        CatPhoto(16, "g7h8i9", "https://example.com/cat16.jpg", 210, 180),
        CatPhoto(17, "j0k1l2", "https://example.com/cat17.jpg", 230, 220),
        CatPhoto(18, "m3n4o5", "https://example.com/cat18.jpg", 250, 190),
        CatPhoto(19, "p6q7r8", "https://example.com/cat19.jpg", 180, 210),
        CatPhoto(20, "s9t0u1", "https://example.com/cat20.jpg", 200, 220)
    )
}