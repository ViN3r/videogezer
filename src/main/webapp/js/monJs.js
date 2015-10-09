/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 var player = null;
    var controls = document.getElementById("controls");
    var playButton = document.getElementById("play_button");
    var muteButton = document.getElementById("mute_button");
    var progressPlayed = document.getElementById("progress");
    
    var muted = false;
    var currentVolume = 1.0;
    
    function ToggleVideo() {
      
      player = document.querySelector('.view1 #home_video ,  .view2 #clip2');
      
      if (player.ended){
        player.currentTime = 0;
        player.pause();
      }
      
      if(player.paused) {
        
       player.addEventListener("timeupdate", function(e) {
          var duration = player.duration;
          var pos = player.currentTime;
          progressPlayed.style.width = parseInt((pos / duration) *100) +"%";
        },false);
        
        player.play();
        playButton.innerText = "Pause"
      }
      else {
        player.pause();
        playButton.innerText = "Play";
      }
    }
    
    function ToggleMute() {
      
      player = document.querySelector('.view1 #home_video, .view2 #clip2');
      
      if (Math.round(player.volume * 100) == 5){ 
        muted = false;
      } else {
        muted = !muted;
      }
      if (muted) {
        currentVolume = player.volume;
        player.volume = 0;
        muteButton.innerText = "Unmute";
      }
      else {
        player.volume = currentVolume;
        muteButton.innerText = "Mute";
      }
    }
    
    
    document.querySelector('#video-wrap').addEventListener('click', function(e){
      // if clicked on minimized
      if (e.target == document.querySelector('.view1 #clip2 ,  .view2 #home_video')){ 
        toggle();
        return;
      } 

      var wrap = document.getElementById('video-wrap');
      wrap.className = wrap.className.replace('playing','playingtemp').replace('paused','playing').replace('playingtemp','paused');

     


    },false);
    
    
    function fadeIn(elem){
      var duration = 1000; // how many ms should the fadeIn take
      var interval = 40;
      
      elem.volume = 0.05;
      
      var tId = setInterval(function(){
        
        var newvol = elem.volume + (1 / (duration / interval));
        elem.volume = Math.min(1,newvol);
        
        if (elem.volume >= .95){
          clearInterval(tId);
          elem.volume = 1;
        }
      }, interval);
      
      ToggleMute();
    };
    
    function toggle(){
      var minimized = document.querySelector('.view1 #clip2, .view2 #home_video');
      var full = document.querySelector('.view2 #clip2, .view1 #home_video');

      var wrap = document.getElementById('video-wrap');
      wrap.className = wrap.className.replace('view1','save1')
                              .replace('view2','view1').replace('save1','view2')
                              .replace('paused','playing');
                              
      //full.pause && full.pause();
      fadeIn(minimized);
     // minimized.play && ToggleVideo();

    };
  

