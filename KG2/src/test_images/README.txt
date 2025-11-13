Test images for image processing (sharpening, morphological, compression testing)

Files included:
- flat_color.png             : large uniform area (very good compression)
- gradient.png               : smooth gradient (good compression)
- checkerboard.png           : high-frequency pattern (poor compression)
- random_noise.png           : pure noise (worst compression)
- text_shapes.png            : scene with text and shapes (general-purpose tests)
- scene_blurred.png          : blurred version of scene (for sharpening)
- scene_gaussian_noise.png   : scene with Gaussian noise (denoising/sharpening)
- low_contrast.png           : reduced contrast (contrast enhancement tests)
- scene_jpeg_high_q.jpg      : scene saved as high-quality JPEG
- scene_jpeg_low_q.jpg       : scene saved as low-quality JPEG (visible artifacts)
- binary_shapes.png          : binary shapes (morphological operations testing)
- thin_branches.png          : thin, branch-like structures (morphological opening/closing)
- checker_motion_blur.png    : checkerboard with motion blur (sharpening tests)

Suggested uses:
- High-frequency / sharpening: checkerboard.png, scene_blurred.png, checker_motion_blur.png
- Morphological processing (choose structuring element): binary_shapes.png, thin_branches.png
- Compression extremes:
    * Best compression candidates: flat_color.png, gradient.png
    * Worst compression candidates: random_noise.png, checkerboard.png

Image sizes: mostly 1024x768. All images are synthetic so you can reproduce results deterministically.

