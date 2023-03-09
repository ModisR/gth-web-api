# Gallifreyan Translation Helper Web API

The goal of this project is to provide a more convenient interface with which the
[Discord bot](https://github.com/Mightyfrong/gth-discord-bot) can interact. Currently, it uses the clunkier approach of
[puppeteer-ing](https://github.com/puppeteer/puppeteer) the
[Gallifreyan Translation Helper web page](https://github.com/Mightyfrong/gallifreyan-translation-helper).

This API is in the proof of concept stage, merely demonstrating the ability to:

1. process apostrophes, hyphens, and the letters A-Z and
2. group them together into appropriate stacks as specified by [Loren Sherman](https://shermansplanet.com/gallifreyan/guide.pdf)

when the endpoint `GET /v1/gth/translate/:text` is called.

## JSON

When calling the endpoint `GET /v1/gth/translate/:text` with the path parameter "I most definitely am a madman with a box",
you get the following:

```json
[
  [
    {
      "vowels": {
        "head": {
          "base": "Middle",
          "decoration": "LineUp",
          "asString": "I"
        },
        "decorations": []
      }
    }
  ],
  [
    {
      "consonants": {
        "head": {
          "base": "CircleIn",
          "deco": "Lines(3)",
          "asString": "M"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Top",
          "decoration": "Plain",
          "asString": "O"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "ArcMinor",
          "deco": "Lines(3)",
          "asString": "S"
        },
        "decorations": [
          "Dots(0)"
        ]
      }
    }
  ],
  [
    {
      "consonants": {
        "head": {
          "base": "ArcMajor",
          "deco": "Dots(3)",
          "asString": "D"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Middle",
          "decoration": "Plain",
          "asString": "E"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "ArcMajor",
          "deco": "Lines(3)",
          "asString": "F"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Middle",
          "decoration": "LineUp",
          "asString": "I"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleIn",
          "deco": "Lines(1)",
          "asString": "N"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Middle",
          "decoration": "LineUp",
          "asString": "I"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "ArcMinor",
          "deco": "Dots(0)",
          "asString": "T"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Middle",
          "decoration": "Plain",
          "asString": "E"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleIn",
          "deco": "Dots(3)",
          "asString": "L"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleOut",
          "deco": "Dots(2)",
          "asString": "Y"
        },
        "decorations": []
      }
    }
  ],
  [
    {
      "vowels": {
        "head": {
          "base": "Bottom",
          "decoration": "Plain",
          "asString": "A"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleIn",
          "deco": "Lines(3)",
          "asString": "M"
        },
        "decorations": []
      }
    }
  ],
  [
    {
      "vowels": {
        "head": {
          "base": "Bottom",
          "decoration": "Plain",
          "asString": "A"
        },
        "decorations": []
      }
    }
  ],
  [
    {
      "consonants": {
        "head": {
          "base": "CircleIn",
          "deco": "Lines(3)",
          "asString": "M"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Bottom",
          "decoration": "Plain",
          "asString": "A"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "ArcMajor",
          "deco": "Dots(3)",
          "asString": "D"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleIn",
          "deco": "Lines(3)",
          "asString": "M"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Bottom",
          "decoration": "Plain",
          "asString": "A"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleIn",
          "deco": "Lines(1)",
          "asString": "N"
        },
        "decorations": []
      }
    }
  ],
  [
    {
      "consonants": {
        "head": {
          "base": "ArcMinor",
          "deco": "Lines(2)",
          "asString": "W"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Middle",
          "decoration": "LineUp",
          "asString": "I"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleOut",
          "deco": "Dots(0)",
          "asString": "TH"
        },
        "decorations": []
      }
    }
  ],
  [
    {
      "vowels": {
        "head": {
          "base": "Bottom",
          "decoration": "Plain",
          "asString": "A"
        },
        "decorations": []
      }
    }
  ],
  [
    {
      "consonants": {
        "head": {
          "base": "ArcMajor",
          "deco": "Dots(0)",
          "asString": "B"
        },
        "decorations": []
      },
      "vowels": {
        "head": {
          "base": "Top",
          "decoration": "Plain",
          "asString": "O"
        },
        "decorations": []
      }
    },
    {
      "consonants": {
        "head": {
          "base": "CircleOut",
          "deco": "Lines(2)",
          "asString": "X"
        },
        "decorations": []
      }
    }
  ]
]```