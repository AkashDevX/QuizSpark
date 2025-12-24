#!/usr/bin/env python3
"""
Script to download celebrity images for the quiz app
Run: python download_images.py
"""

import os
import urllib.request
import urllib.error

# Create assets directory if it doesn't exist
assets_dir = "app/src/main/assets/celebrity_images"
os.makedirs(assets_dir, exist_ok=True)

# Image URLs - using reliable sources
# Note: These are example URLs. Replace with actual celebrity image URLs
image_urls = {
    "tom_hanks.jpg": "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Tom_Hanks_TIFF_2019.jpg/400px-Tom_Hanks_TIFF_2019.jpg",
    "robert_downey_jr.jpg": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Robert_Downey_Jr_2014_Comic_Con_%28cropped%29.jpg/400px-Robert_Downey_Jr_2014_Comic_Con_%28cropped%29.jpg",
    "beyonce.jpg": "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Beyonc%C3%A9_at_The_Lion_King_European_Premiere_2019.png/400px-Beyonc%C3%A9_at_The_Lion_King_European_Premiere_2019.png",
    "michael_jackson.jpg": "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Michael_Jackson_in_1988.jpg/400px-Michael_Jackson_in_1988.jpg",
    "oprah_winfrey.jpg": "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Oprah_Winfrey_2010.jpg/400px-Oprah_Winfrey_2010.jpg",
    "lebron_james.jpg": "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/LeBron_James_%2851959977144%29_%28cropped%29.jpg/400px-LeBron_James_%2851959977144%29_%28cropped%29.jpg"
}

print("Downloading celebrity images...")

for filename, url in image_urls.items():
    filepath = os.path.join(assets_dir, filename)
    try:
        print(f"Downloading {filename}...")
        urllib.request.urlretrieve(url, filepath)
        print(f"✓ Successfully downloaded {filename}")
    except Exception as e:
        print(f"✗ Failed to download {filename}: {e}")

print("\nDownload complete!")
print(f"Images saved to: {os.path.abspath(assets_dir)}")

